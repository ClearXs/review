package com.jw.basics;

public enum SimpleEnum {

    NEW,

    TEST;

    public static SimpleEnum getEnum(int val) {
        if ((val & 1) == 0 ) {
            return NEW;
        } else {
            return TEST;
        }
    }
}
