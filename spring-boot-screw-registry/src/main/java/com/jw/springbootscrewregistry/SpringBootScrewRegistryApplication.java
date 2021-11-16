package com.jw.springbootscrewregistry;

import com.zzht.patrol.screw.spring.boot.EnableScrewRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableScrewRegistry
public class SpringBootScrewRegistryApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootScrewRegistryApplication.class, args);
    }

}
