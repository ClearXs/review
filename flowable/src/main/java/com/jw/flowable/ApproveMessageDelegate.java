package com.jw.flowable;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

import java.util.Map;

public class ApproveMessageDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) {
        Map<String, Object> variables = execution.getVariables();
        variables.entrySet().forEach(System.out::println);
        System.out.println("通过消息");
    }
}
