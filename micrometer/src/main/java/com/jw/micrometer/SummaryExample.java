package com.jw.micrometer;

import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

public class SummaryExample {

    public static void main(String[] args) {
        SimpleMeterRegistry registry = new SimpleMeterRegistry();
        DistributionSummary summary = registry.summary("summary");
    }
}
