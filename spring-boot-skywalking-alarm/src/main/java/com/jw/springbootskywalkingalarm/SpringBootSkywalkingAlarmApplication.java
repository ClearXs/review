package com.jw.springbootskywalkingalarm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SpringBootSkywalkingAlarmApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSkywalkingAlarmApplication.class, args);

        RestTemplate restTemplate = new RestTemplate();
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(100);
                restTemplate.getForObject("http://127.0.0.1:8090/exception", String.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
