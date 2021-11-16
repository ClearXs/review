package com.jw;


import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FieldAccessor;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

public class FieldValueAnnotation {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, IOException {
        Proxy proxy = new ByteBuddy()
                .subclass(Proxy.class)
                .defineField("name", String.class, Modifier.PUBLIC)
                .implement(NameInterceptor.class)
                .intercept(FieldAccessor.ofBeanProperty())
                .method(ElementMatchers.named("out1"))
                .intercept(MethodDelegation.to(ProxyDelegationWithField.class))
                .make()
                .load(ClassLoader.getSystemClassLoader())
                .getLoaded()
                .newInstance();
        proxy.getClass().getMethod("setName", String.class).invoke(proxy, "2121");
        String s = proxy.out1("212");
        System.out.println(s);

        new ByteBuddy()
                .subclass(Proxy.class)
                .defineField("name", String.class, Modifier.PUBLIC)
                .implement(NameInterceptor.class)
                .intercept(FieldAccessor.ofBeanProperty())
                .method(ElementMatchers.named("out1"))
                .intercept(MethodDelegation.to(ProxyDelegationWithField.class))
                .make()
                .saveIn(new File("D://"));
    }
}
