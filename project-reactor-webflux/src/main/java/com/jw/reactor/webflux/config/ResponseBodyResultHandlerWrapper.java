package com.jw.reactor.webflux.config;

import com.jw.reactor.webflux.R;
import org.springframework.core.MethodParameter;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.reactive.HandlerResult;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;
import org.springframework.web.reactive.result.method.annotation.ResponseBodyResultHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

public class ResponseBodyResultHandlerWrapper extends ResponseBodyResultHandler {

    public ResponseBodyResultHandlerWrapper(List<HttpMessageWriter<?>> writers, RequestedContentTypeResolver resolver, ReactiveAdapterRegistry registry) {
        super(writers, resolver, registry);
    }

    @Override
    public Mono<Void> handleResult(ServerWebExchange exchange, HandlerResult result) {
        Object returnValue = result.getReturnValue();
        // 单数据
        if (returnValue instanceof Mono) {
            returnValue =
                    ((Mono<Object>) returnValue)
                            .map(R::success)
                            .defaultIfEmpty(R.success(""));
            // 多数据
        } else if (returnValue instanceof Flux) {
            returnValue =
                    ((Flux<Object>) returnValue)
                            .collectList()
                            .map(R::success)
                            .defaultIfEmpty(R.success(Collections.EMPTY_LIST));
            // 产生异常
        } else {
            ServerHttpResponse response = exchange.getResponse();
            if (returnValue instanceof R) {
                response.setStatusCode(HttpStatus.valueOf(
                        ((R) returnValue).getCode()
                ));
            } else {
                returnValue = R.exception(new UnknownError());
            }
        }
        MethodParameter bodyTypeParameter = result.getReturnTypeSource();
        return writeBody(returnValue, bodyTypeParameter, exchange);
    }

}
