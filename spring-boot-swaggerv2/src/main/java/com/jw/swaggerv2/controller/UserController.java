package com.jw.swaggerv2.controller;

import com.jw.swaggerv2.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Api(tags = "用户接口")
public class UserController {


    @GetMapping("/get")
    @ApiOperation("根据用户id获取用户信息")
    public User get(long id) {
        User user = new User();
        user.setId(id);
        return user;
    }
}
