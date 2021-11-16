package com.jw.flowable.event.message;

import com.jw.flowable.ProcessUtil;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.delegate.JavaDelegate;
import org.flowable.engine.runtime.Execution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MessageDelegate implements JavaDelegate {

    protected void invoke(String messageName) {
        ProcessEngine processEngine = ProcessUtil.of().getProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        List<Execution> executions = runtimeService.createExecutionQuery().messageEventSubscriptionName(messageName).list();
        Map<String, Object> variable = new HashMap<>();
        variable.put("is_" + messageName, true);
        executions.forEach(o ->
                runtimeService.messageEventReceived(messageName, o.getId(), variable));
    }
}
