package com.jw.flowable.task;

import org.flowable.common.engine.api.delegate.Expression;
import org.flowable.task.service.delegate.DelegateTask;
import org.flowable.task.service.delegate.TaskListener;

public class ReAssgineeListner implements TaskListener {

    private Expression test;

    @Override
    public void notify(DelegateTask delegateTask) {
        Object value = test.getValue(delegateTask);
        System.out.println(value);
        delegateTask.setAssignee("qqq");
    }
}
