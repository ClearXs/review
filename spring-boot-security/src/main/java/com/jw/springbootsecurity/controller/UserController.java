package com.jw.springbootsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user/api")
@RestController
public class UserController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }


}
