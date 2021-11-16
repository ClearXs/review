package com.jw.unittest.mapper;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.jw.unittest.BaseTest;
import com.jw.unittest.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Todo
 *
 * @author jw
 * @date 2021/11/13 11:20
 */
public class UserMapperTest extends BaseTest {

    @Mock
    UserMapper userMapper;

    @Test
    public void testGetUserById() {
        // 1.测试返回的User对象是否为空
        assertNotNull(userMapper.get("1"));
        // 2.测试返回的User对象是否是需要的User
        assertEquals("1", userMapper.get("1").getId());
    }

    @BeforeEach
    @Override
    public void init() {
        MockitoAnnotations.openMocks(this);
        User user = new User();
        user.setId("1");
        when(userMapper.get(anyString())).thenReturn(user);
    }

    @Override
    public void tearDown() {

    }
}
