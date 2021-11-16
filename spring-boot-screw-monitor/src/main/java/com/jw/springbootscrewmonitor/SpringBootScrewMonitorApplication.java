package com.jw.springbootscrewmonitor;

import com.zzht.patrol.screw.spring.boot.EnableScrewMonitor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableScrewMonitor
public class SpringBootScrewMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootScrewMonitorApplication.class, args);
    }

}
