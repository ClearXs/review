package com.jw.micrometer;

import io.micrometer.core.instrument.LongTaskTimer;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import java.util.concurrent.TimeUnit;

public class LongTaskTimerExample {

    public static void main(String[] args) throws InterruptedException {

        SimpleMeterRegistry registry = new SimpleMeterRegistry();
        LongTaskTimer timer = registry.more().longTaskTimer("long");

        timer.record(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(333);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        double duration = timer.duration(TimeUnit.MICROSECONDS);
        System.out.println(duration);
    }
}
