package com.jw.basics;

import java.time.ZonedDateTime;

public class Simple {

    private int i;

    public Simple(int i) {
        this.i = i;
        if (i > 2) {
            throw new IllegalArgumentException("参数大于2");
        }
        System.out.println(this.i);
    }

    public static void main(String[] args) {
//        Simple simple = new Simple(3);

        boolean contains = "3232".contains("34");
        System.out.println(contains);

        ZonedDateTime now = ZonedDateTime.now();
        System.out.println(now);
    }
}
