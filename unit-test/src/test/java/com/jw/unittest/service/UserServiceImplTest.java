package com.jw.unittest.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.jw.unittest.BaseTest;
import com.jw.unittest.mapper.UserMapper;
import com.jw.unittest.entity.User;
import com.jw.unittest.message.DingTalkTrigger;
import com.jw.unittest.message.PlatformMessageInvoker;
import com.jw.unittest.message.SmsTrigger;
import com.jw.unittest.service.impl.UserServiceImpl;
import com.jw.unittest.vo.MessageVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.time.Duration;

public class UserServiceImplTest extends BaseTest {

    @Spy
    UserServiceImpl userService;

    @Mock
    UserMapper userMapper;

    @Spy
    PlatformMessageInvoker invoker;

    /**
     * 条件覆盖测试
     */

    @Test
    public void testMapperNull() {
        userService.setUserMapper(null);
        // 因为是异步调用，所以主线程需要延迟，故采用timeout方式进行测试
        assertTimeout(Duration.ofMillis(1000), () -> {
            assertThrows(NullPointerException.class, () -> userService.asyncSendMessage(MessageVo.of()));
        });
    }

    @Test
    public void testInvokerNull() {
        userService.setInvoker(null);
        assertTimeout(Duration.ofMillis(1000), () -> {
            assertThrows(NullPointerException.class, () -> userService.asyncSendMessage(MessageVo.of()));
        });
    }

    @Test
    public void testTriggerNull() {
        assertTimeout(Duration.ofMillis(1000), () -> {
            assertThrows(NullPointerException.class, () -> userService.asyncSendMessage(MessageVo.of()));
        });
    }

    @Test
    public void testSmsTrigger() {
        MessageVo message = MessageVo.of();
        message.setType(SmsTrigger.class.getName());
        assertTimeout(Duration.ofMillis(1000), () -> userService.asyncSendMessage(message));
    }

    @Test
    public void testDingTalkTrigger() {
        MessageVo message = MessageVo.of();
        message.setType(DingTalkTrigger.class.getName());
        assertTimeout(Duration.ofMillis(1000), () -> userService.asyncSendMessage(message));
    }

    @BeforeEach
    @Override
    public void init() {
        MockitoAnnotations.openMocks(this);
        // 打桩
        User user = new User();
        user.setId("1");
        when(userMapper.get(anyString())).thenReturn(user);
        userService.setUserMapper(userMapper);
        userService.setInvoker(invoker);
    }

    @Override
    public void tearDown() {

    }
}
