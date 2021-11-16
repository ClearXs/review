package com.jw.springboottest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class TestController {

    @GetMapping("test")
    public String test() throws UnknownHostException {
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        return hostAddress;
    }
}
