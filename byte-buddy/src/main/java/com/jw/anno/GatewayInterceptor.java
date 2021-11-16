package com.jw.anno;

import net.bytebuddy.implementation.bind.annotation.AllArguments;
import net.bytebuddy.implementation.bind.annotation.Origin;

import java.lang.reflect.Method;

public class GatewayInterceptor {

    public static String intercept() {
        return "";
    }

    public static String intercept(@Origin Method method, @AllArguments Object[] args) {
        System.out.println(method);
        for (Object arg : args) {
            System.out.println(arg);
        }
        return args[0].toString();
    }
}
