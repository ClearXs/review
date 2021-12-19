package com.jw.projectreactor;

import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FluxAndMono {

    public static void main(String[] args) {
        Flux<String> flux = Flux.just("foo", "bar", "foobar");

        // 对每一个元素进行消息
        flux.subscribe(System.out::println);

        // 对正常元素进行消费，
        Flux<Integer> range = Flux.range(0, 3)
                .map(i -> {
                    if (i == 3) {
                        throw new NullPointerException();
                    }
                    return i;
                });
        range.subscribe(System.out::println, System.out::println);

        // 对正常元素、错误响应，并且序列完成正常后进行回调
        Flux.range(0, 3)
                .map(i -> i + 1)
                .filter(i -> i != 3)
                .subscribe(System.out::println,
                        error -> error.printStackTrace(),
                        () -> System.out.println("done"));

        // 对正常元素、错误和完成信号均有响应，并定义对subscribe方法的回调
        MySubscriber<Integer> subscriber = new MySubscriber<>();
        Flux<Integer> rangeFlux = Flux.range(0, 5);
        rangeFlux.subscribe(
                System.out::println,
                Throwable::printStackTrace,
                () -> System.out.println("done"),
                s -> subscriber.request(10)
        );
        rangeFlux.subscribe(subscriber);

        // 自定义背压
        flux.map(String::toUpperCase)
                .subscribe(new BaseSubscriber<String>() {
                    @Override
                    protected void hookOnNext(String value) {
                        System.out.println(value);
                    }
                });
    }

    static class MySubscriber<T> extends BaseSubscriber<T> {

        @Override
        protected void hookOnNext(T value) {
            System.out.println(value);
            // request表示我这只能消费一个，不要发多了
            request(1);
        }
    }

}
