package com.jw.flowable.event.message;

import com.jw.flowable.ProcessUtil;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.runtime.ProcessInstance;

public class MessageEvent {

    public static void main(String[] args) {
        ProcessEngine processEngine = ProcessUtil.of().getProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();

        repositoryService.createDeployment().addClasspathResource("bpmn/event/messageEvent.bpmn20.xml").deploy();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        // 根据消息名称启动流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByMessage("start", "1");

    }
}
