package com.jw.flowablespringboot.controller;

import com.jw.flowablespringboot.service.ProcessService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.flowable.task.api.Task;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController("/process")
@AllArgsConstructor
public class ProcessController {

    private ProcessService processService;

    @GetMapping("start")
    public void start() {
        processService.start();
    }

    @GetMapping("getTasks")
    public List<TaskInfo> getTasks() {
        List<Task> taskList = processService.getTaskList();
        return taskList.stream()
                .map(task -> new TaskInfo(task.getId(), task.getName()))
                .collect(Collectors.toList());
    }

    @Data
    @AllArgsConstructor
    public static class TaskInfo {
        private String id;

        private String name;
    }

}
