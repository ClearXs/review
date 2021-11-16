package com.jw.reactor.webflux.service;

import com.jw.reactor.webflux.vo.SystemVo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface SystemService {

    Flux<SystemVo> list();

    Mono<SystemVo> get(String id);

    Mono<Boolean> add(Mono<SystemVo> system);

    Mono<Boolean> update(Mono<SystemVo> system);

    Mono<Boolean> delete(String id);
}
