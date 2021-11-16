package com.jw.unittest.springboottest.service;

import static org.junit.jupiter.api.Assertions.*;

import com.jw.unittest.message.Invoker;
import com.jw.unittest.message.PlatformMessageInvoker;
import com.jw.unittest.service.UserService;
import com.jw.unittest.service.impl.UserServiceImpl;
import com.jw.unittest.springboottest.ContextTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

class UserServiceTest extends ContextTest {

    @BeforeEach
    @Override
    public void init() {
        super.init();
        context.register(UserServiceConfiguration.class);
    }

    @Test
    void testSendMessage() {
        UserService service = context.getBean(UserService.class);
        assertNotNull(service);
    }

    @Configuration
    static class UserServiceConfiguration {

        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }

        @Bean
        public Invoker invoker() {
            return new PlatformMessageInvoker();
        }

    }
}
