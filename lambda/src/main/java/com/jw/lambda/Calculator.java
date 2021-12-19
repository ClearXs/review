package com.jw.lambda;

import java.util.function.Supplier;

@FunctionalInterface
public interface Calculator extends Supplier<Integer> {

    Integer get();
}
