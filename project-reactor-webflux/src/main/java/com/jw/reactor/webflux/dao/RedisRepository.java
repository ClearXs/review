package com.jw.reactor.webflux.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class RedisRepository implements IRepository {

    @Autowired
    private ReactiveRedisTemplate redisTemplate;


    @Override
    public <T> Mono<Boolean> insert(Publisher<T> newData) {
        return null;
    }

    @Override
    public <T> Mono<Boolean> update(Publisher<T> incrementalData) {
        return Flux.from(incrementalData)
                .collectList()
                .flatMap(list ->
                        redisTemplate.opsForList()
                                .leftPushAll("test", list.stream().map(RedisRepository::writeValue))
                                .doOnNext(o -> System.out.println(o.toString()))).then(Mono.just(true))
    }

    @Override
    public <T> Mono<Boolean> delete(Publisher<T> freeze) {
        return null;
    }

    private static <T> T readValue(String data, Class<T> element) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(data, element);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String writeValue(Object value) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }
}
