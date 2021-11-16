package com.jw.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Map;

public class QuartzManager {

    private static SchedulerFactory schedulerFactory = new StdSchedulerFactory();

    private final static String JOB = "job";

    private final static String TRIGGER = "trigger";

    public static void addJob(Class<? extends Job> jobClass, String jobName, int interval, Map<String, Object> data) throws SchedulerException {
        Scheduler scheduler = schedulerFactory.getScheduler();
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, JOB).build();
        jobDetail.getJobDataMap().putAll(data);
        SimpleTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName, TRIGGER)
                .startAt(DateBuilder.futureDate(1, DateBuilder.IntervalUnit.MINUTE))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(interval).repeatForever())
                .build();
        scheduler.scheduleJob(jobDetail, trigger);
        if (!scheduler.isShutdown()) {
            scheduler.start();
        }
    }

    public static void removeJob(String jobName) throws SchedulerException {
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.deleteJob(new JobKey(jobName, JOB));
    }
}
