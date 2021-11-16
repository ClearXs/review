package com.jw.springbootscrew;

import com.zzht.patrol.screw.spring.boot.EnableScrewConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableScrewConsumer(serverKey = "6758fcdc0da017540d11889c22bb5a6e", configKey = "config_center", monitorKey = "monitor_center")
public class SpringBootScrewApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootScrewApplication.class, args);
    }

}
