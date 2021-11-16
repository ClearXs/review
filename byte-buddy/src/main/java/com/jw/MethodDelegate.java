package com.jw;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

public class MethodDelegate {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {

        Proxy proxy = new ByteBuddy()
                .subclass(Proxy.class)
                .method(ElementMatchers.named("out1").or(ElementMatchers.named("out2")))
                .intercept(MethodDelegation.to(ProxyDelegation.class))
                .make()
                .load(ClassLoader.getSystemClassLoader())
                .getLoaded()
                .newInstance();

        System.out.println(proxy.out1("22"));
        System.out.println(proxy.out2("21", "212"));
    }
}
