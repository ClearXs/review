package com.jw.spring.event;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfig {

    @Bean
    public CustomService customService() {
        return new CustomService();
    }

    @Bean
    public CustomListener customListener() {
        return new CustomListener();
    }

}
