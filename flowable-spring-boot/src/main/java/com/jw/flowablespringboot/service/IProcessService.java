package com.jw.flowablespringboot.service;

import org.flowable.task.api.Task;

import java.util.List;

public interface IProcessService {

    void start();

    List<Task> getTaskList();
}
