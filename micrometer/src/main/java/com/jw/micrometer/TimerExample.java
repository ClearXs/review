package com.jw.micrometer;

import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import java.util.concurrent.TimeUnit;

public class TimerExample {

    public static void main(String[] args) throws InterruptedException {
        SimpleMeterRegistry registry = new SimpleMeterRegistry();
        Timer timer = Timer.builder("timer").description("").tags(Tags.empty()).register(registry);
        timer.record(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2222);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        System.out.println(timer);
        double v = timer.totalTime(TimeUnit.SECONDS);
        System.out.println(v);
    }
}
