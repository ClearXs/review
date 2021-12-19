package com.jw.reactor.webflux.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jw.reactor.webflux.vo.SystemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private ReactiveRedisTemplate redisTemplate;

    @Override
    public Flux<SystemVo> list() {
        // 模拟从redis获取数据为在从数据库获取数据并入redis中
        return redisTemplate.opsForList().range("test", 0, -1)
                .switchIfEmpty(Flux.empty());
    }

    @Override
    public Mono<SystemVo> get(String id) {
        return redisTemplate.opsForList().range("test", 0, -1)
                .map(v -> readValue((String) v, SystemVo.class))
                .filter(f -> ((SystemVo) f).getId().equals(id))
                .switchIfEmpty(Mono.defer(() -> {
                    SystemVo of = SystemVo.of(id);
                    return redisTemplate.opsForList().leftPush("test", writeValue(of)).then(Mono.just(of));
                }))
                .cast(SystemVo.class)
                .single();
    }

    @Override
    public Mono<Boolean> add(Mono<SystemVo> systemVo) {
        return Mono.defer(() -> {
            return systemVo.map(v -> redisTemplate.opsForList().leftPush("test", writeValue(v)).filter(p -> {
                        System.out.println(p);
                        return false;
                    }))
                    .then(Mono.just(true));
        });
    }

    @Override
    public Mono<Boolean> update(Mono<SystemVo> systemVoMono) {
        // 1.更新数据库数据
        // 2.更新缓存数据
        // 2.1 获取所有的缓存数据
        // 2.2 关键值匹配更新
        // 2.3 删除缓存
        // 2.4 插入新的缓存
        // 2.5 此时用户是否能够
        return Mono.defer(() -> Mono.just(Boolean.TRUE));
    }

    @Override
    public Mono<Boolean> delete(String id) {
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
