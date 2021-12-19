package com.jw.swaggerv3.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("test")
public class TimeoutController {

    @GetMapping
    public Mono<String> timeout() throws InterruptedException {
        Thread.sleep(5000);
        return Mono.just("timeout");
    }
}
