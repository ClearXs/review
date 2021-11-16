package com.jw.springbootactuator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.jw.springbootactuator.*")
public class SpringBootActuatorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootActuatorApplication.class, args);
    }

}
