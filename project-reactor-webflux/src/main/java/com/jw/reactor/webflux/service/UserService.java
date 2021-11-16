package com.jw.reactor.webflux.service;

import com.jw.reactor.webflux.vo.UserVo;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UserService {

    List<UserVo> list();

    UserVo get(String id);

    Mono<Boolean> add(UserVo userVo);

    Mono<Boolean> update(UserVo userVo);

    Mono<Boolean> delete(String id);

}
