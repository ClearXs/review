package com.jw.flowable.task;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngines;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstanceBuilder;

public class UserTask {

    public static void main(String[] args) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();

        Deployment deploy = repositoryService.createDeployment().addClasspathResource("bpmn/task/userTask.bpmn20.xml").deploy();

        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploy.getId()).singleResult();


        RuntimeService runtimeService = processEngine.getRuntimeService();

        ProcessInstanceBuilder processInstanceBuilder = runtimeService.createProcessInstanceBuilder();

        processInstanceBuilder.processDefinitionId(processDefinition.getId());

        processInstanceBuilder.start();

    }
}
