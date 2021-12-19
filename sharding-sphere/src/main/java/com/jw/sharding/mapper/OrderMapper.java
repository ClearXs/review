package com.jw.sharding.mapper;

import com.jw.sharding.entity.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {

    Order selectOrderById(Integer id);

    List<Order> selectOrdersByUserId(Integer userId);

    void insert(Order order);
}
