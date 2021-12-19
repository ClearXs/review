package com.jw.projectreactor;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxTest {

    @Test
    void functionDownstream() {
        Flux.just("1")
                .map(toList())
                .subscribe(list -> list.forEach(System.out::println));
    }

    public Function<String, List<Integer>> toList() {
        // -> 这种写法等价于 .map(input -> Arrays.asList(Integer.valueOf(input)))
        return input -> Arrays.asList(Integer.valueOf(input));
    }

    @Test
    void testZipWith() {
        // zip可以理解成与的操作 压缩多个流中的数据至多以流最少的数据为准，当最少流没有数据，则数据不会往下发送
        Flux<Integer> source1 = Flux.just(1, 2);
        Flux<Integer> source2 = Flux.just(1);
        StepVerifier.create(Flux.zip(source2, source1, Integer::sum))
                .expectNext(2)
                .verifyComplete();
        Flux<Integer> source3 = Flux.empty();

        StepVerifier.create(source1.zipWith(source3, Integer::sum))
                .verifyComplete();
    }

    @Test
    void testConcatMap() {
        // concat操作可以理解成 s1流的数据与s2流的数据组合
        Flux<Integer> source1 = Flux.just(1);
        Flux<Integer> source2 = Flux.just(1, 2);
        StepVerifier.create(source1
                        .concatMap(s2 -> source2.map(s1 -> s1 + s2)))
                .expectNext(2)
                .expectNext(3)
                .verifyComplete();


    }
}
