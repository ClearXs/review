package com.jw.springbootjaeger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class JaegerController {


    @GetMapping("opentracing")
    public String opentracing() {
        return "opentracing";
    }

    @GetMapping("test")
    public void test() {
        RestTemplate restTemplate = new RestTemplate();
        String forObject = restTemplate.getForObject("http://localhost:8080/opentracing", String.class);
        System.out.println(forObject);
    }
}
