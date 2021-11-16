package com.jw.reactor.webflux.controller;

import com.jw.reactor.webflux.dto.UserDTO;
import com.jw.reactor.webflux.service.UserService;
import com.jw.reactor.webflux.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public Flux<UserVo> getUser() {
        return Flux.fromIterable(userService.list());
    }

    @GetMapping("/get/{id}")
    public Mono<UserVo> get(@PathVariable("id") String id) {
        return Mono.just(userService.get(id));
    }

    @PostMapping("/add")
    public Mono<Boolean> add(UserDTO add) {
        return userService.add(add);
    }

    @PutMapping("/update")
    public Mono<Boolean> update(UserDTO update) {
        return userService.update(update);
    }

    @DeleteMapping("/delete/{id}")
    public Mono<Boolean> delete(@PathVariable("id") String id) {
        return userService.delete(id);
    }
}
