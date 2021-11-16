package com.jw.springbootsecurity.controller;

import com.jw.springbootsecurity.module.system.user.UserService;
import com.jw.springbootsecurity.module.system.user.domain.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admin/api")
@RestController
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public String hello() {
        return "hello admin";
    }

    @GetMapping("/getUser")
    public UserEntity getUser(String username) {
        return userService.getUserByUsername(username);
    }
}
