package com.jw.reactor.webflux;

import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class ResultResposnse<T, K extends ServerResponse> {

    public static <T> Mono<ServerResponse> onSuccess(T data) {
        return ServerResponse.ok().bodyValue(R.success(data));
    }

    public static <T> Mono<ServerResponse> onSuccess(Mono<T> data) {
        return ServerResponse.ok().body(BodyInserters.fromPublisher(data.map(R::success), R.class));
    }

    public static Mono<ServerResponse> onFailure(Exception e) {
        return ServerResponse.badRequest().bodyValue(R.exception(e));
    }

    public static Mono<ServerResponse> onFailure(Throwable e) {
        return ServerResponse.badRequest().bodyValue(R.exception(e));
    }
}
