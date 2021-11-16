package com.jw.micrometer;

import io.micrometer.core.instrument.Timer;

import java.time.Duration;

public class HistogramsExample {

    public static void main(String[] args) {
        Timer.builder("timer")
                // 发布中位数、百分比
                .publishPercentiles(0.5, 0.95)
                // 发布直方图
                .publishPercentileHistogram()
                // 边界
                .sla(Duration.ofMillis(100))
                // 最小值
                .minimumExpectedValue(Duration.ofMillis(1))
                // 最大值
                .maximumExpectedValue(Duration.ofSeconds(10));
    }
}
