package com.jw.flowable.event;

import com.jw.flowable.ProcessUtil;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;

public class SignalStartEvent {

    public static void main(String[] args) {
        ProcessEngine processEngine = ProcessUtil.of().getProcessEngine();

        RepositoryService repositoryService = processEngine.getRepositoryService();

        Deployment deploy = repositoryService.createDeployment().addClasspathResource("bpmn/event/throwSignalEvent.bpmn20.xml").deploy();

        RuntimeService runtimeService = processEngine.getRuntimeService();

        runtimeService.signalEventReceived("startSignal");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("throwSignalEvent");

    }


}
