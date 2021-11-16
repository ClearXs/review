package com.jw.lambda;

import java.util.function.Function;

public class Cascade {

    public static void main(String[] args) {
        Function<Integer, Function<Integer, Integer>> curry = x -> y -> x + y;
        System.out.println(curry.apply(1).apply(2));
    }
}
