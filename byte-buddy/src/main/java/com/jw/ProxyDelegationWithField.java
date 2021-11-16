package com.jw;

import net.bytebuddy.implementation.bind.annotation.FieldValue;

public class ProxyDelegationWithField {

    public static String out(String param1, @FieldValue(value = "name") String name) {
        return "ProxyDelegationWithField: p1: " + param1 + " name: " + name;
    }
}
