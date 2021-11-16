package com.jw.micrometer;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.Tags;
import io.micrometer.core.instrument.search.RequiredSearch;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GaugeExample {

    public static void main(String[] args) {
        SimpleMeterRegistry registry = new SimpleMeterRegistry();
        AtomicInteger number = registry.gauge("number", new AtomicInteger());
        number.set(1);

        // 监视非数组对象
        ArrayList<String> list = registry.gauge("list", Collections.emptyList(), new ArrayList<String>(), List::size);

        // 监视集合大小
        ArrayList<Object> list2 = registry.gaugeCollectionSize("list2", Tags.empty(), new ArrayList<>());

        // 监视map
        HashMap<Object, Object> map = registry.gaugeMapSize("map", Tags.empty(), new HashMap<>());
    }
}
