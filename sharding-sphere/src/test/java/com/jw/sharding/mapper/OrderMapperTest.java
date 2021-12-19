package com.jw.sharding.mapper;

import com.jw.sharding.BaseApplicationTest;
import com.jw.sharding.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class OrderMapperTest extends BaseApplicationTest {

    @Autowired
    private OrderMapper orderMapper;

    @Test
    void testInsertOrder() {
        Order mock = Mockito.mock(Order.class);
        Mockito.when(mock.getUserId()).thenReturn(2);
        orderMapper.insert(mock);
    }

    @Test
    void testSelectOrderById() {
        Order order = orderMapper.selectOrderById(1);
        Assertions.assertNotNull(order);
        log.info(order.toString());
    }

    @Test
    void testOrdersByUserId() {
        List<Order> orders = orderMapper.selectOrdersByUserId(1);
        Assertions.assertNotNull(orders);
        orders.forEach(o -> log.info(o.toString()));
    }
}
