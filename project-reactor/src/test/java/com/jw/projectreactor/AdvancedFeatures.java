package com.jw.projectreactor;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.UnicastProcessor;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class AdvancedFeatures {

    @Test
    public void testTransform() {
//        List<String> color = Arrays.asList("blue", "green", "purple");
        // 去除数据序列中blue的元素
        Function<Flux<String>, Flux<String>> filterChain
                = f ->
                f.filter(color -> !color.equals("blue"))
                        .map(String::toUpperCase);
        Flux.fromIterable(Arrays.asList("blue", "green", "purple"))
                .doOnNext(System.out::println)
                .transform(filterChain)
                .subscribe(s -> System.out.println("filter color: " + s));

    }

    @Test
    public void testTrans() {
        AtomicInteger num = new AtomicInteger();
        Flux<String> compose = Flux.fromIterable(Arrays.asList("blue", "green", "purple"))
                .doOnNext(System.out::println)
                .transformDeferred (f -> {
                    if (num.incrementAndGet() == 1) {
                        return f.filter(color -> !color.equals("blue"))
                                .map(String::toUpperCase);
                    }
                    return f.filter(color -> !color.equals("green"))
                            .map(String::toUpperCase);
                });

        compose.subscribe(v -> System.out.println("subscribe chain1 filter color: " + v));
        compose.subscribe(v -> System.out.println("subscribe chain2 filter color: " + v));
    }

    @Test
    public void testHot() {
        Sinks.Many<String> hot
                = Sinks.unsafe().many().multicast().directBestEffort();
        Flux<String> flux = hot.asFlux().map(String::toUpperCase);
        flux.subscribe(d -> System.out.println("Subscribe 1 to hot source: " + d));

        hot.emitNext("blue", Sinks.EmitFailureHandler.FAIL_FAST);
        hot.tryEmitNext("green").orThrow();

        flux.subscribe(d -> System.out.println("Subscribe 2 to hot source: " + d));

        hot.emitNext("orange", Sinks.EmitFailureHandler.FAIL_FAST);

        hot.emitComplete(Sinks.EmitFailureHandler.FAIL_FAST);
    }

    @Test
    public void testConnectableFlux() {
        Flux<Integer> source
                = Flux.range(1, 3).doOnSubscribe(s -> System.out.println("on subscriber"));
        ConnectableFlux<Integer> publish = source.publish();

        publish.subscribe(System.out::println, System.out::println, System.out::println);
        publish.subscribe(System.out::println, System.out::println, System.out::println);

        publish.connect();

    }

    /**
     * 测试自动触发订阅
     */
    @Test
    public void testConnectableFluxAutoConnect() {
        Flux<Integer> source
                = Flux.range(1, 3).doOnSubscribe(s -> System.out.println("on subscriber"));
        Flux<Integer> publish = source.publish()
                .autoConnect(2);
        publish.subscribe(System.out::println, System.out::println, System.out::println);
        publish.subscribe(System.out::println, System.out::println, System.out::println);
    }

    @Test
    public void testConnectableFluxRefCount() throws InterruptedException {
        Flux<Integer> source =
                Flux.range(1, 3).doOnSubscribe(s -> System.out.println("on subscriber"));
        Flux<Integer> publish = source.publish().refCount(2);

        publish.subscribe(System.out::println);


        System.out.println("sleep 1000");
        Thread.sleep(1000);

        publish.subscribe(System.out::println);
    }

}
