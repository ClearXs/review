package com.jw.unittest.springboottest;

import static org.junit.jupiter.api.Assertions.*;

import com.jw.unittest.entity.User;
import com.jw.unittest.mapper.UserMapper;
import com.jw.unittest.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;

@SpringBootTest
public class SpringMockBeanTest {

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private UserService userService;

    @Test
    void testMockEnvironment(@Autowired Environment environment) {
        assertEquals("root", environment.getProperty("spring.datasource.username"));
    }

    @Test
    void testUserMapperIsExisting() {
        assertNotNull(userMapper);
    }

    @Test
    void testUserServiceIsExisting() {
        assertNotNull(userService);
    }

    @Test
    void testGetUserById() {
        User user = userMapper.get("1299246290848841729");
        assertNotNull(user);
        assertEquals("1299246290848841729", user.getId());
    }
}
