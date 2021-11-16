package com.jw.micrometer;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import java.util.Collection;

public class CounterExample {

    public static void main(String[] args) {
        SimpleMeterRegistry registry = new SimpleMeterRegistry();

        // 获取Counter的两种方式
        // 方式一
        Counter counter1 = registry.counter("counter1", "t1", "v1");

        // 方式二
        Counter counter2 = Counter.builder("counter2").baseUnit("number").description("test1").tag("t2", "v2").register(registry);
        counter2.increment();
        counter2.increment(22);

    }
}
