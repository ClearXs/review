package com.jw.projectreactor;

import reactor.core.publisher.Flux;

public class Handle {

    public static void main(String[] args) {
        Flux.just(1, -1, 2)
                .handle((value, sink) -> {
                    if (value > -1) {
                        sink.next(value.toString());
                    }
                })
                .subscribe(System.out::println);
    }
}
