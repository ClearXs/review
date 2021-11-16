package com.jw.flowable;

import lombok.Data;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.flowable.engine.impl.el.FixedValue;

import java.util.Map;

@Data
public class RejectMessageDelegate implements JavaDelegate {

    private FixedValue test;

    @Override
    public void execute(DelegateExecution execution) {
        Map<String, Object> variables = execution.getVariables();
        variables.entrySet().forEach(System.out::println);
        System.out.println("reject message");
        execution.setVariable("test", "test");
        execution.setVariableLocal("local", "local");
    }
}
