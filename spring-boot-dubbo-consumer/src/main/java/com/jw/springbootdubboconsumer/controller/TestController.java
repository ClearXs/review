package com.jw.springbootdubboconsumer.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jw.springbootdubboconsumer.service.DemoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Reference(url = "dubbo://localhost:20880")
    private DemoService demoService;

    @GetMapping("hello")
    public String hello() {
        return demoService.hello();
    }
}
