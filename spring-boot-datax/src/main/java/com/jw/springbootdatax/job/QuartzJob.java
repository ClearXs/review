package com.jw.springbootdatax.job;

import com.jw.springbootdatax.JobHandler;
import com.jw.springbootdatax.config.DataXConfig;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@Slf4j
public class QuartzJob implements Job {

    @Autowired
    private DataXConfig dataXConfig;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        boolean enable = dataXConfig.isEnable();
        if (!enable) {
            return;
        }
        try {
            executeDataXJob(dataXConfig.getJobFile());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void executeDataXJob(String[] jobFile) throws IOException, InterruptedException {
        // 读文件 修改文件 创建临时文件
        // 线程池专门处理执行命令
        // 日志线程记录日志
        JobHandler handler = JobHandler.getInstance(dataXConfig);
        for (String job : jobFile) {
            handler.push(job);
        }
    }
}
