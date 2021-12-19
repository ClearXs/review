package com.jw.boot.basic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

public class RedisKeyExpiredTest {

    private Jedis jedis;

    @BeforeEach
    void setup() {
        jedis = new Jedis(RedisConstant.HOST, RedisConstant.PORT);
    }

    @Test
    void testKeyExpiredSub() {
        jedis.subscribe(new RedisPubSubListener(), "__keyevent@0__:expired");
    }
}
