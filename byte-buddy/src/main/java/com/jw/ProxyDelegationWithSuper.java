package com.jw;

import net.bytebuddy.implementation.bind.annotation.Super;

public class ProxyDelegationWithSuper {

    public static String out1(String param1, @Super Proxy proxy) {
        long start = System.currentTimeMillis();
        String s = proxy.out1(param1);
        System.out.println(System.currentTimeMillis()- start);
        return s;
    }
}
