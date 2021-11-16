package com.jw.basics.thread;

import java.util.concurrent.*;

/**
 * 异步任务，执行完成后，返回执行结果
 * @author jiangw
 * @date 2020/10/24 15:36
 * @since 1.0
 */
public class SimpleTask implements Callable<Integer> {


    @Override
    public Integer call() throws Exception {
        Thread.sleep(1000);
        return 2121;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        SimpleTask simpleTask = new SimpleTask();
        Future<Integer> submit = executorService.submit(simpleTask);
        System.out.println(submit.get());
    }
}
