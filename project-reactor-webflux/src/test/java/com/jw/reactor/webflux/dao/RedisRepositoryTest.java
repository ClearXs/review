package com.jw.reactor.webflux.dao;

import com.jw.reactor.webflux.BaseTest;
import com.jw.reactor.webflux.vo.SystemVo;
import java.time.Duration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

public class RedisRepositoryTest extends BaseTest {

    @Autowired
    private RedisRepository redisRepository;

    @Test
    void testUpdate() {
        Flux<SystemVo> incremental = Flux.just(
                SystemVo.of("1"),
                SystemVo.of("2")
        );
        Assertions.assertTimeout(Duration.ofMillis(5000), () -> redisRepository.update(incremental));
    }
}
