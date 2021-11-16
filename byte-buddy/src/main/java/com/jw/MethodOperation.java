package com.jw;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class MethodOperation {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Object o = new ByteBuddy()
                .subclass(Object.class)
                .name("com.jw.Enhance")
                .defineMethod("helloWorld", String.class, Modifier.PUBLIC)
                .intercept(FixedValue.value("Hello World!"))
                .make()
                .load(ClassLoader.getSystemClassLoader())
                .getLoaded()
                .newInstance();
        Method method = o.getClass().getMethod("helloWorld");
        System.out.println(method.invoke(o));
    }
}
