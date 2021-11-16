package com.jw.reactor.webflux.vo;

import lombok.Data;

import java.util.UUID;

@Data
public class UserVo {

    private String id;

    private String username;

    private String password;

    public static UserVo of() {
        UserVo userVo = new UserVo();
        userVo.setId(UUID.randomUUID().toString());
        return userVo;
    }

    public static UserVo of(String id) {
        UserVo userVo = new UserVo();
        userVo.setId(id);
        return userVo;
    }
}
