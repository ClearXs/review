package com.jw.flowable.form;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.*;
import org.flowable.engine.FormService;
import org.flowable.engine.form.StartFormData;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.form.api.*;
import org.flowable.form.engine.FormEngine;
import org.flowable.form.engine.FormEngines;
import org.flowable.task.api.Task;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class FlowableForm {

    public static void main(String[] args) {

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        RepositoryService repositoryService = processEngine.getRepositoryService();

        // 1.部署流程
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("bpmn/event/holiday.bpmn20.xml")
                .deploy();

        // 2.部署表单
        FormEngine formEngine = FormEngines.getDefaultFormEngine();
        FormRepositoryService formRepositoryService = formEngine.getFormRepositoryService();
        FormDeployment formDeployment = formRepositoryService.createDeployment()
                .addClasspathResource("form/holiday.form")
                .deploy();
        FormDefinition formDefinition = formRepositoryService.createFormDefinitionQuery().deploymentId(formDeployment.getId()).singleResult();

        // 3.设置启动表单
        Map<String, Object> formProperties = new HashMap<>();
        DateTime dt = DateTime.now(DateTimeZone.UTC);
        formProperties.put("reason", "家里有事");
        formProperties.put("startTime", "2016-02-12");
        formProperties.put("endTime", "2016-02-12");

        // 4.启动流程
        RuntimeService runtimeService = processEngine.getRuntimeService();
        // 四个参数
        // 1.processDefinitionId 流程定义id
        // 2.outcome ?
        // 3.variables 表单数据
        // 4.processInstanceName 流程实例名称
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deploy.getId())
                .singleResult();
        ProcessInstance processInstance = runtimeService.startProcessInstanceWithForm(processDefinition.getId(), "", formProperties, "");


        // 5.获取启动流程表达数据
        // 两个参数
        // 1.processDefinitionId：流程定义id
        // 2.processInstanceId：流程实例id
        FormInfo formInfo = runtimeService.getStartFormModel(processDefinition.getId(), processInstance.getId());
        log.info("key: {}", formInfo.getKey());
        log.info("name: {}", formInfo.getName());
        log.info("variables: {}", formInfo.getFormModel());
        // 6.查询当前流程实例的任务并填写表单完成任务
        TaskService taskService = processEngine.getTaskService();
        // 根据流程实例id查询任务
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        // 完成任务，四个参数
        // 1.taskId：任务id
        // 2.formDefinitionId：表单定义id
        // 3.outcome：完成表单结果
        // 4.variables：表单变量
        Map<String, Object> formVariables = new HashMap<>();
        formVariables.put("reason", "家里有事2222");
        formVariables.put("startTime", dt.toString());
        formVariables.put("endTime", dt.toString());
        formVariables.put("days", "3");

        taskService.completeTaskWithForm(task.getId(), formDefinition.getId(), "", formVariables);

        // 获取表单数据
        FormInfo taskFormModel = taskService.getTaskFormModel(task.getId());
        log.info("key: {}", taskFormModel.getKey());
        log.info("name: {}", taskFormModel.getName());
        log.info("variables: {}", taskFormModel.getFormModel());
    }
}
