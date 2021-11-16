package com.jw.projectreactor;

import org.junit.Test;
import reactor.core.publisher.Mono;

public class MonoTest {

    @Test
    public void test() {
        Integer block = Mono.justOrEmpty("123")
                .map(s -> s.length())
                .block();
        System.out.println(block);
    }
}
