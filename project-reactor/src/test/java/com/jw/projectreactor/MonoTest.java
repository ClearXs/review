package com.jw.projectreactor;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

public class MonoTest {

    private Map<Object, Object> cache;

    @BeforeEach
    void setup() {
        cache = new HashMap<>();
        cache.put(1, "1");
    }

    @Test
    public void test() {
        Integer block = Mono.justOrEmpty("123")
                .map(s -> s.length())
                .block();
        System.out.println(block);
    }

    @Test
    void flatMapTest() {
        getCache("1")
                .switchIfEmpty(Mono.justOrEmpty(cache.get(2))
                        .map(v -> (Integer) v)
                        .flatMap(v -> Mono.just(v)))
                .onErrorResume(error -> {
                    error.printStackTrace();
                    return Mono.just("1");
                });
    }

    <E> Mono<E> getCache(Object key) {
        return (Mono) Mono.defer(() -> {
            Object v = cache.get(key);
            if (v == null) {
                return Mono.empty();
            }
            return Mono.just(v);
        });
    }
}
