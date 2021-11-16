package com.jw.springbootconsumer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValueConfig {

    @Value("${redis.port:1}")
    private String port;

    public String getPort() {
        return port;
    }
}
