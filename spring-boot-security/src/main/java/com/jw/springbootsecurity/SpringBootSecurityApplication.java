package com.jw.springbootsecurity;

import com.zzht.patrol.screw.spring.boot.EnableScrewConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableScrewConsumer(serverKey = "fdbc77bd19b9ce6318b00649645095cc", configKey = "config_center", monitorKey = "monitor_center")
public class SpringBootSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSecurityApplication.class, args);
    }

}
