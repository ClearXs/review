package com.jw.unittest.springboottest.mapper;

import com.jw.unittest.entity.User;
import com.jw.unittest.mapper.UserMapper;
import com.jw.unittest.springboottest.ContextTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * 构建spring上下文环境，为了避免直接使用@ContextTest
 *
 * @author jw
 * @date 2021/11/13 14:51
 */
public class UserMapperTest extends ContextTest {

    /**
     * 测试mapper接口，sql由注解提供
     */
    @Test
    void testGetUserByIdOfAnnotationsSql() {
        UserMapper userMapper = context.getBean(UserMapper.class);
        // 1.测试User Mapper是否为空
        Assertions.assertNotNull(userMapper);
        // 2.测试获取对象是否为null
        User user = userMapper.get("1299246290848841729");
        Assertions.assertNotNull(user);
        // 3.测试对象是否正确
        Assertions.assertEquals("1299246290848841729", user.getId());
    }
}
