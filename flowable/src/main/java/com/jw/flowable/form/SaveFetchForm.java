package com.jw.flowable.form;

import cn.hutool.core.io.FileUtil;
import org.flowable.engine.FormService;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngines;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.form.engine.FormEngine;
import org.flowable.form.engine.FormEngines;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 操作flowable怎么存放和获取表单
 */
public class SaveFetchForm {

    public static void main(String[] args) {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        ByteArrayInputStream form = new ByteArrayInputStream(FileUtil.readBytes("form/holiday.form"));
        ByteArrayInputStream bpmn = new ByteArrayInputStream(FileUtil.readBytes("bpmn/event/holiday.bpmn20.xml"));
        DeploymentBuilder deployment = repositoryService.createDeployment();

        deployment.addInputStream("holiday.form", form);
        deployment.addInputStream("holiday.bpmn20.xml", bpmn);

        Deployment deploy = deployment.deploy();

        List<String> names = repositoryService.getDeploymentResourceNames(deploy.getId());
        names.forEach(name -> {
            if (name.endsWith("form") || name.endsWith("xml")) {
                try {
                    InputStream stream = repositoryService.getResourceAsStream(deploy.getId(), name);
                    byte[] bytes = new byte[stream.available()];
                    stream.read(bytes);
                    String s = new String(bytes);
                    System.out.println(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        // 读取表单数据
        FormService formService = processEngine.getFormService();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploy.getId()).singleResult();
        Object renderedStartForm = formService.getRenderedStartForm(processDefinition.getId());
        System.out.println(renderedStartForm);
    }
}
