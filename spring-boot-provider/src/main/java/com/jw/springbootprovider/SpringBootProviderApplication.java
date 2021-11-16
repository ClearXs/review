package com.jw.springbootprovider;

import com.zzht.patrol.screw.spring.boot.EnableScrewConsumer;
import com.zzht.patrol.screw.spring.boot.EnableScrewProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@EnableScrewProvider(serverKey = "provider1", serverPort = 9001, monitorKey = "monitor_center")
@EnableScrewConsumer(serverKey = "3c59dc048e8850243be8079a5c74d079", configKey = "config_center", monitorKey = "monitor_center", serverPort = 9001)
public class SpringBootProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootProviderApplication.class, args);
    }

}
