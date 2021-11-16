package com.jw.micrometer;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.binder.jvm.ClassLoaderMetrics;
import io.micrometer.core.instrument.config.MeterFilter;
import io.micrometer.core.instrument.config.MeterFilterReply;
import io.micrometer.core.instrument.config.NamingConvention;
import io.micrometer.core.instrument.distribution.DistributionStatisticConfig;
import io.micrometer.core.instrument.search.RequiredSearch;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NamingMeter {

    public static void main(String[] args) {
        SimpleMeterRegistry registry = new SimpleMeterRegistry();
        registry.config().namingConvention(new NamingConvention() {
            @Override
            public String name(String name, Meter.Type type, String baseUnit) {
                return name + type.name() + baseUnit;
            }
        });
        Timer timer = registry.timer("test.tst");
        System.out.println(timer);
        Counter test1 = registry.counter("test1");
        test1.increment();
        Timer timer1 = registry.timer("n1", "k1", "k2");
        new ClassLoaderMetrics().bindTo(registry);
        ArrayList<Object> gauge = registry.gauge("test.test", Collections.emptyList(), new ArrayList<>(), List::size);
        System.out.println(gauge);

    }

    class CustomizeFilter implements MeterFilter {

        @Override
        public MeterFilterReply accept(Meter.Id id) {
            if(id.getName().contains("test")) {
                return MeterFilterReply.DENY;
            }
            return MeterFilterReply.NEUTRAL;
        }

        @Override
        public Meter.Id map(Meter.Id id) {
            return null;
        }

        @Override
        public DistributionStatisticConfig configure(Meter.Id id, DistributionStatisticConfig config) {
            return null;
        }
    }
}
