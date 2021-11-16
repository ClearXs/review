package com.jw.springbootsecurity.module.system.user;

import com.jw.springbootsecurity.module.system.user.domain.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

    UserEntity loadUserByUsername(String username);
}
