package com.jw.reactor.webflux.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfiguration {

    @Bean
    public ReactiveRedisTemplate<String, Object> redisTemplate(ReactiveRedisConnectionFactory factory) {
        // 第一次见泛型这种写法
        RedisSerializationContext<String, Object> redisContext = RedisSerializationContext.<String, Object>newSerializationContext(RedisSerializer.string())
                .value(RedisSerializer.json())
                .build();
        return new ReactiveRedisTemplate<>(factory, redisContext);
    }
}
