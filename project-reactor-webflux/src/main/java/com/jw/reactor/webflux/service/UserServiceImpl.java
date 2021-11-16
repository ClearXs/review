package com.jw.reactor.webflux.service;

import com.jw.reactor.webflux.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Override
    public List<UserVo> list() {
        return Arrays.asList(UserVo.of("1"));
    }

    @Override
    public UserVo get(String id) {
        if ("1".equals(id)) {
            throw new RuntimeException();
        }
        return UserVo.of(id);
    }

    @Override
    public Mono<Boolean> add(UserVo userVo) {
        log.info(userVo.toString());
        return Mono.just(true);
    }

    @Override
    public Mono<Boolean> update(UserVo userVo) {
        log.info(userVo.toString());
        return Mono.just(true);
    }

    @Override
    public Mono<Boolean> delete(String id) {
        log.info(id);
        return Mono.just(true);
    }
}
