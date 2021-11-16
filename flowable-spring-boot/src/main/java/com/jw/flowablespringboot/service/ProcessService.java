package com.jw.flowablespringboot.service;

import lombok.AllArgsConstructor;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("MyProcess")
@AllArgsConstructor
public class ProcessService implements IProcessService {

    private RuntimeService runtimeService;
    private TaskService taskService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void start() {
        runtimeService.startProcessInstanceByKey("Leave");
    }

    @Override
    public List<Task> getTaskList() {
        return taskService.createTaskQuery().list();
    }
}
