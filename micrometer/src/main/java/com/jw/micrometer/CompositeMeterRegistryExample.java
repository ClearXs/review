package com.jw.micrometer;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

public class CompositeMeterRegistryExample {

    public static void main(String[] args) {
        // composite 支持多个MeterRegistry，从而允许同时发布数据到多个监控系统。
        CompositeMeterRegistry registry = new CompositeMeterRegistry();
        registry.add(new SimpleMeterRegistry());
        Counter counter = registry.counter("m");
        counter.increment();

    }
}
