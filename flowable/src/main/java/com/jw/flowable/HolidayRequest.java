package com.jw.flowable;

import org.flowable.engine.*;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HolidayRequest {

    public static void main(String[] args) {
        ProcessEngineConfiguration root = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:mysql://localhost:3306/flowable?autoReconnect=true&useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8")
                .setJdbcUsername("root")
                .setJdbcPassword("123456")
                .setJdbcDriver("com.mysql.cj.jdbc.Driver")
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        // 创建流程引擎
        ProcessEngine processEngine = root.buildProcessEngine();

        // 流程部署至Flowable引擎，通过使用RepositoryService可以通过XML文件创建一个Deployment
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("LeaveProcess.bpmn20.xml")
                .deploy();
        // 查找流程定义
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deploy.getId())
                .singleResult();
        System.out.println(processDefinition);

        // 启动流程实例，并设置变量
        Map<String, Object> variables = new HashMap<>();
        variables.put("employee", "jw");
        RuntimeService runtimeService = processEngine.getRuntimeService();
        // 这里使用的key就是流程定义的id
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Leave", variables);
        System.out.println(processInstance);

        // 查询任务
        // 1.查询第一个用户任务，参与者为jw
        TaskService taskService = processEngine.getTaskService();
        List<Task> employee = taskService.createTaskQuery().taskAssignee((String) variables.get("employee")).list();
        for (Task task : employee) {
            taskService.complete(task.getId(), new HashMap<>());
        }
        // 2.查询第二个用户任务，候选组为manager
        List<Task> managers = taskService.createTaskQuery().taskCandidateGroup("manager").list();
        System.out.println(managers);

        for (Task task : managers) {
            String name = task.getName();
            System.out.println(name);
            Map<String, Object> processVariables = task.getProcessVariables();
            Map<String, Object> variables1 = taskService.getVariables(task.getId());
            System.out.println(processVariables.equals(variables1));
            // 完成任务
            Map<String, Object> taskVariable = new HashMap<>();
            // 填写网关条件
            taskVariable.put("approved", false);
            taskService.complete(task.getId(), taskVariable);
        }

        HistoryService historyService = processEngine.getHistoryService();
        // 查询历史的流程实例根据id,按照结束时间进行降序排序
        List<HistoricProcessInstance> list = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(processInstance.getId())
                .finished()
                .orderByProcessInstanceEndTime()
                .desc()
                .list();

        for (HistoricProcessInstance historicProcessInstance : list) {
            Map<String, Object> processVariables = historicProcessInstance.getProcessVariables();
            processVariables.entrySet().forEach(System.out::println);
            // 流程实例执行时间
            System.out.println(historicProcessInstance.getId() + ":" + historicProcessInstance.getDurationInMillis());
        }
    }
}
