package com.jw.reactor.webflux.config;

import com.jw.reactor.webflux.R;
import com.jw.reactor.webflux.service.SystemService;
import com.jw.reactor.webflux.vo.SystemVo;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class SystemRoute {

    @Autowired
    private SystemService systemService;

    @Bean
    public RouterFunction<ServerResponse> routes() {
        return RouterFunctions.route(RequestPredicates.GET("/system/list"), request -> ServerResponse.ok().body(
                        decorationBody(systemService.list())
                ))
                .and(RouterFunctions.route(RequestPredicates.GET("/system/get/{id}"), request ->
                        ServerResponse.ok().body(
                                decorationBody(systemService.get(request.pathVariable("id")))
                        )))
                .and(RouterFunctions.route(RequestPredicates.POST("/system/add"), request -> {
                    Mono<SystemVo> systemVoMono = request.bodyToMono(SystemVo.class);
                    return ServerResponse.ok().body(
                            decorationBody(systemService.add(systemVoMono))
                    );
                }));
    }

    public static <T> BodyInserter<?, ? super ServerHttpResponse> decorationBody(Publisher<T> publisher) {
        if (publisher instanceof Mono) {
            return BodyInserters.fromPublisher(
                    ((Mono) publisher).map(R::success),
                    R.class
            );
        }
        if (publisher instanceof Flux) {
            return BodyInserters.fromPublisher(
                    ((Flux) publisher).map(R::success),
                    R.class
            );
        }
        throw new IllegalStateException("unknow publisher type: " + publisher.getClass().getName());
    }
}
