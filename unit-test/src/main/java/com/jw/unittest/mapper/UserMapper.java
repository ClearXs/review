package com.jw.unittest.mapper;

import com.jw.unittest.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    /**
     * 根据id获取用户
     * @param id
     * @return
     */
    @Select("SELECT * FROM sys_user WHERE id=#{id}")
    User get(String id);
}
