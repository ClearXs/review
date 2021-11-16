package com.jw.projectreactor;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

import java.io.Closeable;
import java.io.IOException;
import java.time.Duration;

@Slf4j
public class HandleError {

    public static void main(String[] args) {
        TaskExecutors.of().submit(() -> {
//            staticDefaultValue(2);
//            exceptionHandleMethod(5);
//            catchExceptionAndThrow(5);
//            onError(5);
//            tryCatch();
//            doFinally();
            onError();


        });
    }

    /**
     * 静态缺省值
     */
    public static void staticDefaultValue(int value) {
        Flux.just(value)
                .map(HandleError::throwError)
                .onErrorReturn("error")
                .subscribe(System.out::println);
    }

    public static void exceptionHandleMethod(int value) {
        Flux.just(value)
                .map(HandleError::throwError)
                .onErrorResume(error -> {
                    if (error instanceof NullPointerException) {
                        return Flux.just("null");
                    }
                    if (error instanceof UnsupportedOperationException) {
                        return Flux.just("unknow");
                    }
                    return Flux.error(error);
                })
                .onErrorReturn("error")
                .subscribe(System.out::println);
    }

    public static void catchExceptionAndThrow(int value) {
        Flux.just(value)
                .map(HandleError::throwError)
                .onErrorMap(error -> new BusinessException(error.getCause()))
                .onErrorResume(error -> {
                    if (error instanceof BusinessException) {
                        return Flux.just("business");
                    }
                    return Flux.just("error");
                })
                .subscribe(System.out::println);
    }

    public static void onError(int value) {
        Flux.just(value)
                .map(HandleError::throwError)
                .doOnError(error -> log.error("error", error))
                .subscribe(System.out::println);
    }

    public static void tryCatch() {
        Resource resourceInstance = new Resource();
        Flux.using(
                () -> resourceInstance,
                resource -> Flux.just(resource.doSomething()),
                Resource::close
        )
                .subscribe(System.out::println);
    }

    public static void doFinally() {
        Flux.just("foo", "bar")
                .doFinally(type -> {
                    if (type == SignalType.CANCEL) {
                        System.out.println("clean");
                    }
                })
                .take(1)
                .subscribe(System.out::println);
    }

    public static void onError() {
        Flux.interval(Duration.ofMillis(250))
                .map(input -> {
                    if (input < 3) {
                        return "tick " + input;
                    }
                    throw new RuntimeException("boom");
                })
                .onErrorReturn("ooo")
                .subscribe(System.out::println);
    }

    public static String throwError(int value) {
        if (value >= 10) {
            throw new NullPointerException();
        }
        if (value < 0) {
            throw new UnsupportedOperationException();
        }
        if (value == 5) {
            throw new Error();
        }
        return String.valueOf(value);
    }


    static class Resource implements Closeable {

        @Override
        public void close() {
            System.out.println("关闭");
        }

        public String doSomething() {
            System.out.println("do something");
            return "something";
        }
    }

    static class BusinessException extends Exception {

        public BusinessException(Throwable cause) {
            super(cause);
        }
    }

}
