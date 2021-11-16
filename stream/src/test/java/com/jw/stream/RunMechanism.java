package com.jw.stream;

import org.junit.Test;

import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Stream运行机制
 *
 * @author jw
 * @date 2021/11/6 18:35
 */
public class RunMechanism {


    @Test
    public void testRun() {
        Random random = new Random();
        Stream.generate(random::nextInt)
                .limit(500)
                .peek(i -> {
                    System.out.println("peek1: " + i);
                })
                .filter(i -> {
                    System.out.println("filter: " + i);
                    return i > 0;
                })
                .distinct()
                .count();
    }
}
