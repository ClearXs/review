package com.jw.springbootconsumer;

import com.zzht.patrol.screw.spring.boot.EnableScrewConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableScrewConsumer(serverKey = "78d2f47e5fa0b4e73bc934ae6b5f197e", configKey = "config_center", monitorKey = "monitor_center")
public class SpringBootConsumerApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SpringBootConsumerApplication.class);
        application.run(args);
    }
}
