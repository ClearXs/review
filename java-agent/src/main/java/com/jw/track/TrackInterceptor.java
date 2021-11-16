package com.jw.track;

import net.bytebuddy.asm.Advice;
import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.Callable;

public class TrackInterceptor {

//    @Advice.OnMethodEnter
//    public static void enter(@Advice.Origin("#t") String className, @Advice.Origin("#t") String methodName) {
//        Span currentSpan = TrackManager.getCurrentSpan();
//        if (currentSpan == null) {
//            TrackContext.setLinkId( UUID.randomUUID().toString());
//        }
//        currentSpan = TrackManager.createCurrentSpan();
//        currentSpan.setStartTime(System.currentTimeMillis());
//    }
//
//    @Advice.OnMethodExit
//    public static void exit(@Advice.Origin("#t") String className, @Advice.Origin("#t") String methodName) {
//        Span exitSpan = TrackManager.getExitSpan();
//        if (exitSpan != null) {
//            exitSpan.setEndTime(System.currentTimeMillis());
//            System.out.println(methodName + " track: " + exitSpan.getName() + " start: " + exitSpan.getStartTime() + " end: " + exitSpan.getEndTime());
//        }
//    }

    @RuntimeType
    public static Object intercept(@Origin Method method, @AllArguments Object[] args, @SuperCall Callable<?> callable) {
        Span currentSpan = TrackManager.getCurrentSpan();
        if (currentSpan == null) {
            TrackContext.setLinkId( UUID.randomUUID().toString());
        }
        currentSpan = TrackManager.createCurrentSpan();
        currentSpan.setStartTime(System.currentTimeMillis());
        try {
            return callable.call();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Span exitSpan = TrackManager.getExitSpan();
            if (exitSpan != null) {
                exitSpan.setEndTime(System.currentTimeMillis());
                System.out.println(Thread.currentThread().getName() + " " +  method.getName() + " track: " + exitSpan.getName() + " start: " + exitSpan.getStartTime() + " end: " + exitSpan.getEndTime());
            }
        }
        return null;
    }
}
