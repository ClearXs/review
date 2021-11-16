package com.jw.flowable.event.message;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

import java.util.Map;

@Slf4j
public class EndMessageDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        Map<String, Object> variables = execution.getVariables();
        variables.entrySet().forEach(System.out::println);
    }
}
