package com.jw.springbootdubboconsumer;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubboConfiguration
public class SpringBootDubboConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDubboConsumerApplication.class, args);
    }

}
