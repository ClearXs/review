package com.jw.track;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import net.bytebuddy.utility.JavaModule;

import java.lang.instrument.Instrumentation;

public class TrackPremain {

    public static void premain(String arg, Instrumentation instrumentation) {
        System.out.println(arg);
        AgentBuilder.Default builder = new AgentBuilder.Default();
        AgentBuilder.Transformer transformer = new AgentBuilder.Transformer() {

            @Override
            public DynamicType.Builder<?> transform(DynamicType.Builder<?> builder, TypeDescription typeDescription, ClassLoader classLoader, JavaModule module) {
                return builder.method(ElementMatchers.any().and(ElementMatchers.not(ElementMatchers.nameStartsWith("TrackManager")))).intercept(MethodDelegation.to(TrackInterceptor.class));
            }
        };
        AgentBuilder.Listener listener = new AgentBuilder.Listener() {

            @Override
            public void onDiscovery(String typeName, ClassLoader classLoader, JavaModule module, boolean loaded) {

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
        builder
            .type(ElementMatchers.nameStartsWith("com.jw.AgentTest"))
            .transform(transformer)
            .with(listener)
            .installOn(instrumentation);
    }
}
