package com.jw.skywalking.springbootskywalking.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("exception")
    public String exception() {
        int i = 1 / 0;
        return "message";
    }
}
