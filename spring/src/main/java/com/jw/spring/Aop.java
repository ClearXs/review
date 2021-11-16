package com.jw.spring;

import org.aspectj.lang.ProceedingJoinPoint;

public class Aop {

    public void log(ProceedingJoinPoint point) throws Throwable {
        Object proceed = point.proceed();
        System.out.println(proceed);
    }
}
