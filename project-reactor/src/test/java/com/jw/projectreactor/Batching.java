package com.jw.projectreactor;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class Batching {

    @Test
    public void testGroupFlux() {
        StepVerifier.create(
                Flux.just(1, 3, 5, 2, 4, 6, 11, 12, 13)
                        .groupBy(i -> i % 2 == 0 ? "even" : "odd")
                        .concatMap(g -> g.defaultIfEmpty(-1) // 如果组为空，显示为-1
                                .map(String::valueOf)        // 转换为字符串
                                .startWith(g.key())          // 以该组的key开头
                        )
        )
                .expectNext("odd", "1", "3", "5", "11", "13")
                .expectNext("even", "2", "4", "6", "12")
                .verifyComplete();
    }

    /**
     * 重叠window测试
     * 根据maxSize skip进行配置开启窗口大小
     */
    @Test
    public void testOverlappingWindow() {
        Flux.range(1, 6)
                .window(5, 4)
                .concatMap(g -> g.defaultIfEmpty(-1))
                .subscribe(System.out::println);
    }

    /**
     * 根据predicates判断是否将元素添加到window中
     */
    @Test
    public void testWindowWhile() {
        Flux.just(1, 3, 5, 2, 4, 6)
                .windowWhile(i -> i % 2 == 0)
                .concatMap(g -> g.defaultIfEmpty(-1))
                .subscribe(System.out::println);
    }

    /**
     * 同window操作，只是不是关闭window，而是发出一个集合
     */
    @Test
    public void testOverlappingBuffer() {
        Flux.range(1, 6)
                .buffer(5, 6)
                .subscribe(System.out::println);
    }

    @Test
    public void testBufferWhile() {
        Flux.just(1, 3, 5, 2, 4, 6)
                .bufferWhile(i -> i % 2 == 0)
                .subscribe(System.out::println);
    }
}
