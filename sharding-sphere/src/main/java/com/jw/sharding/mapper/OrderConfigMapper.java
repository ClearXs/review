package com.jw.sharding.mapper;

import com.jw.sharding.entity.OrderConfig;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderConfigMapper {

    OrderConfig selectOrderConfig(Integer id);
}
