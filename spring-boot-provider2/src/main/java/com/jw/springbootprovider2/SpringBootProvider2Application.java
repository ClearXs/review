package com.jw.springbootprovider2;

import com.zzht.patrol.screw.spring.boot.EnableScrewConsumer;
import com.zzht.patrol.screw.spring.boot.EnableScrewProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableScrewProvider(serverKey = "provider2", serverPort = 9002, monitorKey = "monitor_center")
@EnableScrewConsumer(serverKey = "6364d3f0f495b6ab9dcf8d3b5c6e0b01", configKey = "config_center", monitorKey = "monitor_center", serverPort = 9002)
public class SpringBootProvider2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootProvider2Application.class, args);
    }

}
