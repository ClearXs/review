package com.jw.swaggerv3.controller;

import com.jw.swaggerv3.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
@Api(tags = "用户接口")
public class UserController {

    @GetMapping("/get")
    @ApiOperation("根据用户id获取用户信息")
    public Mono<User> get(long id, ServerWebExchange exchange) {
        List<String> strings = exchange.getRequest().getHeaders().get("X-Access-Token");
        System.out.println(strings);
        User user = new User();
        user.setId(id);
        user.setName("");
        return Mono.just(user);
    }

    @PostMapping("/add")
    @ApiOperation("添加用户")
    public Mono<User> add(@RequestBody User user, ServerWebExchange exchange) {

        return Mono.just(user);
    }

    @GetMapping("/{id}/{name}")
    public Mono<User> get(@PathVariable("id") Long id, @PathVariable("name") String name) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        return Mono.just(user);
    }

    @PostMapping("/{id}/{name}")
    public Mono<User> add(@PathVariable("id") Long id, @PathVariable("name") String name) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        return Mono.just(user);
    }


}
