package com.jw.unittest.mapper;

import com.jw.unittest.entity.Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper {

    Role get(String id);
}
