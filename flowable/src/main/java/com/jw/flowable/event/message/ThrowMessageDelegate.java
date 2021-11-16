package com.jw.flowable.event.message;

import cn.hutool.core.collection.CollectionUtil;
import com.jw.flowable.ProcessUtil;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.query.QueryProperty;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ExecutionQuery;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


@Slf4j
public class ThrowMessageDelegate implements JavaDelegate {

    private ExecutorService sender = new ThreadPoolExecutor(1, 1, 1000, TimeUnit.MICROSECONDS, new LinkedBlockingQueue<>());

    private Lock lock = new ReentrantLock();

    @Override
    public void execute(DelegateExecution execution) {
        log.info("发送消息");
        send("invoice");
        send("payment");
    }

    private void send(final String messageName) {
        lock.lock();
        try {
            AtomicBoolean isTry = new AtomicBoolean(true);
            Condition condition = lock.newCondition();
            ProcessEngine processEngine = ProcessUtil.of().getProcessEngine();
            RuntimeService runtimeService = processEngine.getRuntimeService();
            sender.submit(() -> {
                List<Execution> executions = Collections.emptyList();
                while (CollectionUtil.isEmpty(executions) && isTry.get()) {
                    executions = runtimeService.createExecutionQuery().messageEventSubscriptionName(messageName).list();
                }
                Map<String, Object> variable = new HashMap<>();
                variable.put("is_" + messageName, true);
                executions.forEach(o ->
                        runtimeService.messageEventReceived(messageName, o.getId(), variable));
                condition.signalAll();
            });
            long waitNanos = TimeUnit.MICROSECONDS.toNanos(1000);
            while ((waitNanos = condition.awaitNanos(waitNanos)) <= 0) {
                isTry.set(false);
                return;
            }
            isTry.set(false);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}
