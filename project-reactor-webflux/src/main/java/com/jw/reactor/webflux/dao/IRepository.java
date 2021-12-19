package com.jw.reactor.webflux.dao;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

/**
 * Todo
 * @author jw
 * @date 2021/11/21 23:10
 */
public interface IRepository {

    <T> Mono<Boolean> insert(Publisher<T> newData);

    <T> Mono<Boolean> update(Publisher<T> incrementalData);

    <T> Mono<Boolean> delete(Publisher<T> freeze);
}
