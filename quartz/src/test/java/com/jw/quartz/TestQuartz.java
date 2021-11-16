package com.jw.quartz;

import org.junit.Test;
import org.quartz.SchedulerException;

import java.util.HashMap;
import java.util.Map;

public class TestQuartz {

    @Test
    public void testJon() {
        Map<String, Object> data = new HashMap<>();
        data.put("id", "1");
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        QuartzManager.addJob(TestJob.class, "test", 1, data);
                    } catch (SchedulerException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
