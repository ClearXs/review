package com.jw.flowable;

import cn.hutool.core.io.FileUtil;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngines;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.task.api.Task;

import java.io.File;
import java.io.InputStream;
import java.util.List;

public class ProcessEngineApi {

    public static void main(String[] args) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 1.RepositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();
        // 1.1 基本的使用
        repositoryServiceInfo(repositoryService);
    }

    public static void taskServiceInfo(TaskService taskService) {
        taskService.createTaskQuery().taskAssignee("");
        taskService.createTaskQuery().taskCandidateGroup("");

        taskService.newTask();
        // 指派
        taskService.setAssignee("", "");
        // 转让
        taskService.setOwner("", "");


    }

    public static void repositoryServiceInfo(RepositoryService repositoryService) {
//        // ----- 部署
//        // 从类路径下加载部署包
//        repositoryService.createDeployment().addClasspathResource("").deploy();
//        // 从流中加载
//        repositoryService.createDeployment().addInputStream("", null);
//        // 从压缩文件中加载
//        repositoryService.createDeployment().addZipInputStream(null);

        // 查询act_re_deployment表中所有部署的数据
//        List<Deployment> deployments = repositoryService.createDeploymentQuery().list();
//        System.out.println(deployments);
//
//        // 查询act_re_procdef表中所有的数据
//        List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery().list();
//        System.out.println(processDefinitions);
        repositoryService.suspendProcessDefinitionById("Leave:9:45004");

        InputStream processModel = repositoryService.getProcessModel("Leave:9:45004");
        FileUtil.writeFromStream(processModel, new File("D:\\Leave.bpmn20.xml"));
        ProcessDefinition processDefinition = repositoryService.getProcessDefinition("Leave:9:45004");
        System.out.println(processDefinition);
    }

}
