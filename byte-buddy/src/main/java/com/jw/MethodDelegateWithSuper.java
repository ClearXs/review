package com.jw;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

public class MethodDelegateWithSuper {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        Proxy proxy = new ByteBuddy()
                .subclass(Proxy.class)
                .method(ElementMatchers.named("out1"))
                .intercept(MethodDelegation.to(ProxyDelegationWithSuper.class))
                .make()
                .load(ClassLoader.getSystemClassLoader())
                .getLoaded()
                .newInstance();
        System.out.println(proxy.out1("21"));
    }
}
