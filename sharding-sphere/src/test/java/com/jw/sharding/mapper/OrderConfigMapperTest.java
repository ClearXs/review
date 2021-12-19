package com.jw.sharding.mapper;

import com.jw.sharding.BaseApplicationTest;
import com.jw.sharding.entity.OrderConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class OrderConfigMapperTest extends BaseApplicationTest {

    @Autowired
    private OrderConfigMapper orderConfigMapper;

    @Test
    void testSelectOrderConfig() {
        OrderConfig orderConfig = orderConfigMapper.selectOrderConfig(1);
        Assertions.assertNotNull(orderConfig);
        log.info(orderConfig.toString());
    }

}
