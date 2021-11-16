package com.jw.reactor.webflux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.config.EnableWebFlux;

@SpringBootTest
@EnableWebFlux
@AutoConfigureWebTestClient
public class BaseTest {

    @Autowired
    public WebTestClient client;
}
