package com.jw.reactor.webflux.config;

import com.jw.reactor.webflux.ResultResposnse;
import com.jw.reactor.webflux.service.UserService;
import com.jw.reactor.webflux.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class UserRoute {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @Bean
    public RouterFunction<ServerResponse> userList() {
        // ComposeRoute
        return RouterFunctions.route(RequestPredicates.GET("/user2/list"), request -> ServerResponse.ok().bodyValue(userService.list()))
                .and(
                        RouterFunctions.route(
                                RequestPredicates.GET("/user2/get/{id}"), request -> {
                                    Mono<String> id = Mono.just(request.pathVariable("id"));
                                    try {
                                        return ResultResposnse.onSuccess(id.map(userService::get));
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        return ResultResposnse.onFailure(e);
                                    }
                                }
                        )
                )
                .and(
                        RouterFunctions.route(RequestPredicates.POST("/user2/add"), request -> request.body(BodyExtractors.toMono(UserVo.class))
                                .doOnNext(userService::add)
                                .then(ServerResponse.ok().build())))
                .and(
                        RouterFunctions.route(RequestPredicates.PUT("/user2/update"), request -> request.body(BodyExtractors.toMono(UserVo.class))
                                .doOnNext(userService::update)
                                .then(ServerResponse.ok().build())))
                .and(
                        RouterFunctions.route(RequestPredicates.DELETE("/user2/delete"), request -> request.body(BodyExtractors.toMono(String.class))
                                .doOnNext(userService::delete)
                                .then(ServerResponse.ok().build())));
    }

}
