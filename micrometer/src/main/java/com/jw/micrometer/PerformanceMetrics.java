package com.jw.micrometer;

import io.micrometer.core.instrument.binder.jvm.*;
import io.micrometer.core.instrument.binder.system.FileDescriptorMetrics;
import io.micrometer.core.instrument.binder.system.ProcessorMetrics;
import io.micrometer.core.instrument.binder.system.UptimeMetrics;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class PerformanceMetrics {

    public static void main(String[] args) {

        // jvm.classes.loaded -- 加载classes数
        // jvm.classes.unloaded -- 未加载的classes数
        System.out.println("--------ClassLoaderMetrics-----------");
        getClassLoaderMetrics();

        // disk.total -- 当前目录的总大小
        // disk.free -- 当前目录可用的大小
        System.out.println("--------DiskSpaceMetrics-----------");
        getDiskSpaceMetrics();

        // jvm.gc.memory.promoted -- GC时，老年代分配的内存空间
        // jvm.gc.max.data.size -- GC时，老年代的最大内存空间
        // jvm.gc.memory.allocated -- GC时，年轻代分配的内存空间
        // jvm.gc.live.data.size -- 	FullGC时，老年代的内存空间
        System.out.println("--------JvmGcMetrics-----------");
        getJvmGcMetrics();

        // jvm.memory.max -- JVM最大内存
        // jvm.memory.used	-- JVM已用内存
        // jvm.memory.committed -- JVM可用内存
        System.out.println("--------JvmMemoryMetrics-----------");
        getJvmMemoryMetrics();

        // jvm.threads.states -- JVM守护线程数
        // jvm.threads.live -- JVM当前活跃线程数
        // jvm.threads.peak -- JVM峰值线程数
        System.out.println("--------JvmThreadMetrics-----------");
        getJvmThreadsMetrics();

        System.out.println("--------FileDescriptorMetrics-----------");
        getFileDescriptorMetrics();

        System.out.println("--------ProcessorMetrics-----------");
        // system.cpu.count -- CPU数量
        // system.cpu.usage -- 系统CPU使用率
        // process.cpu.usage -- 当前进程CPU使用率
        getProcessorMetrics();

        System.out.println("--------UptimeMetrics-----------");
        // process.uptime -- 应用已运行时间
        // process.start.time -- 应用开启时间
        getUptimeMetrics();

        System.out.println("--------Properties-----------");
        Properties properties = System.getProperties();
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            System.out.println("properties: key-" + entry.getKey() + " value-" + entry.getValue());
        }

        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        String name = runtimeMXBean.getName();
        System.out.println(name.split("@")[0]);
    }

    public static void getClassLoaderMetrics() {
        SimpleMeterRegistry registry = new SimpleMeterRegistry();
        new ClassLoaderMetrics().bindTo(registry);
        List<Metrics> build = Metrics.build(registry);
        for (Metrics metrics : build) {
            System.out.println(metrics);
        }
    }

    public static void getDiskSpaceMetrics() {
        SimpleMeterRegistry registry = new SimpleMeterRegistry();
        new DiskSpaceMetrics(new File(".")).bindTo(registry);
        List<Metrics> build = Metrics.build(registry);
        for (Metrics metrics : build) {
            System.out.println(metrics);
        }
    }

    public static void getJvmGcMetrics() {
        SimpleMeterRegistry registry = new SimpleMeterRegistry();
        new JvmGcMetrics().bindTo(registry);
        List<Metrics> build = Metrics.build(registry);
        for (Metrics metrics : build) {
            System.out.println(metrics);
        }
    }

    public static void getJvmMemoryMetrics() {
        SimpleMeterRegistry registry = new SimpleMeterRegistry();
        new JvmMemoryMetrics().bindTo(registry);
        List<Metrics> build = Metrics.build(registry);
        for (Metrics metrics : build) {
            System.out.println(metrics);
        }
    }

    public static void getJvmThreadsMetrics() {
        SimpleMeterRegistry registry = new SimpleMeterRegistry();
        new JvmThreadMetrics().bindTo(registry);
        List<Metrics> build = Metrics.build(registry);
        for (Metrics metrics : build) {
            System.out.println(metrics);
        }
    }

    public static void getFileDescriptorMetrics() {
        SimpleMeterRegistry registry = new SimpleMeterRegistry();
        new FileDescriptorMetrics().bindTo(registry);
        List<Metrics> build = Metrics.build(registry);
        for (Metrics metrics : build) {
            System.out.println(metrics);
        }
    }

    public static void getProcessorMetrics() {
        SimpleMeterRegistry registry = new SimpleMeterRegistry();
        new ProcessorMetrics().bindTo(registry);
        List<Metrics> build = Metrics.build(registry);
        for (Metrics metrics : build) {
            System.out.println(metrics);
        }
    }

    public static void getUptimeMetrics() {
        SimpleMeterRegistry registry = new SimpleMeterRegistry();
        new UptimeMetrics().bindTo(registry);
        List<Metrics> build = Metrics.build(registry);
        for (Metrics metrics : build) {
            System.out.println(metrics);
        }
    }

}
