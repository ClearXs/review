package com.jw.stream;

import org.junit.Test;

import java.util.stream.IntStream;

public class ParallelTest {

    @Test
    public void test() {
//        IntStream.range(1, 20).parallel().peek(ParallelTest::print).count();

//        IntStream.range(1, 20).parallel().sequential().peek(ParallelTest::print).count();



    }

    public static void print(int i) {
        System.out.println(Thread.currentThread().getName() + " " + i);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
