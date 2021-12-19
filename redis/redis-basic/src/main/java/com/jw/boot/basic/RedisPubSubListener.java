package com.jw.boot.basic;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisPubSub;

@Slf4j
public class RedisPubSubListener extends JedisPubSub {

    @Override
    public void onMessage(String channel, String message) {
        log.info("channel: {}, message: {}", channel, message);
        this.unsubscribe(channel);
    }
}
