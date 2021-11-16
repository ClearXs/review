package com.jw;

public class ProxyDelegation {

    public static String out1(String param1) {
        return "ProxyDelegation " + param1;
    }

    public static String out2(String param1, String param2) {
        return "ProxyDelegation p1:" + param1 + " p2:" + param2;
    }
}
