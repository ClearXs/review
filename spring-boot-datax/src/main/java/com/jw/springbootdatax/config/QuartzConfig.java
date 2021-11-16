package com.jw.springbootdatax.config;

import com.jw.springbootdatax.job.QuartzJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

    @Autowired
    private DataXConfig dataXConfig;

    @Bean
    public JobDetail testQuartz() {
        return JobBuilder.newJob(QuartzJob.class)
                .withIdentity("quartz", "quartz job")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger testTrigger() {
        boolean enable = dataXConfig.isEnable();
        if (!enable) {
            return null;
        }
        CronScheduleBuilder schedule = CronScheduleBuilder.cronSchedule(dataXConfig.getJobCorn());
        return TriggerBuilder
                .newTrigger()
                .forJob(testQuartz())
                .withIdentity("trigger")
                .withSchedule(schedule)
                .build();
    }

}
