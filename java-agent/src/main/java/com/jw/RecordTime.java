package com.jw;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RecordTime {

    /**
     * javassist实现方法计时
     * @param param
     * @param instrumentation
     */
//    public static void premain(String param, Instrumentation instrumentation) {
//        instrumentation.addTransformer(new JavassistTimeMonitorTransformer());
//    }

    /**
     * byte buddy 实现方法计时
     * @param param
     * @param instrumentation
     */
    public static void premain(String param, Instrumentation instrumentation) {
        System.out.println("arg: " + param);
        AgentBuilder.Transformer transformer = new AgentBuilder.Transformer() {

            @Override
            public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule module) {
                return builder
                        // 拦截任意方法
                        .method(ElementMatchers.nameStartsWith("main"))
                        .intercept(MethodDelegation.to(MethodCostTime.class));
            }
        };

        AgentBuilder.Listener listener = new AgentBuilder.Listener() {

            @Override
            public void onDiscovery(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
                System.out.println(typeName);
            }

            @Override
            public void onTransformation(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded, DynamicType dynamicType) {

            }

            @Override
            public void onIgnored(TypeDescription typeDescription, ClassLoader classLoader, JavaModule module, boolean loaded) {

            }

            @Override
            public void onError(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded, Throwable throwable) {

            }

            @Override
            public void onComplete(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {
            }
        };

        new AgentBuilder.Default()
                // 指定代理的类
                .type(ElementMatchers.nameStartsWith("com.jw"))
                .transform(transformer)
                .with(listener)
                .installOn(instrumentation);
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                PerformanceMetrics.printPerformanceMetrics();
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }
}
