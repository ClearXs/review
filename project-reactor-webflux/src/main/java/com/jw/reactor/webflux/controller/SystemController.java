package com.jw.reactor.webflux.controller;

import com.jw.reactor.webflux.R;
import com.jw.reactor.webflux.service.SystemService;
import com.jw.reactor.webflux.vo.SystemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private SystemService systemService;

    @GetMapping("/getList")
    public Flux<SystemVo> getList() {
        return systemService.list();
    }


}
