package com.jw.boot.basic;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import redis.clients.jedis.Jedis;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 先订阅再发布，否则发布的数据找不到订阅者，则这个数据就发送为空
 *
 * @author jw
 * @date 2021/12/14 22:46
 */
@Execution(ExecutionMode.CONCURRENT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RedisPubSubTest {

    private Jedis jedis;

    @BeforeEach
    void setup() {
        jedis = new Jedis(RedisConstant.HOST, RedisConstant.PORT);
    }

    @Test
    @Order(Integer.MIN_VALUE)
    void testSub() {
        RedisPubSubListener listener = new RedisPubSubListener();
        jedis.subscribe(listener, RedisConstant.SUB_CHANNEL);
    }

    @Test
    @Order(Integer.MAX_VALUE)
    void testPub() {
        Long test = jedis.publish(RedisConstant.SUB_CHANNEL, "test");
        assertTrue(test > 0);
    }
}
