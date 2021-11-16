package com.jw.unittest.util;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.jw.unittest.BaseTest;
import com.jw.unittest.entity.User;
import com.jw.unittest.vo.UserVo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Todo
 * @author jw
 * @date 2021/11/13 10:33
 */
public class ContextUtilTest extends BaseTest {

    /**
     * 假设这个就是当前登录的用户
     */
    @Mock
    User user;

    @Test
    public void testGetCurrentUser() {
        // 1.测试是否能够获取当前用户
        UserVo currentUser = ContextUtil.getCurrentUser();
        assertNotNull(currentUser);
        // 2.测试是否是当前登录的用户
        assertEquals(user.getId(), currentUser.getId());
    }

    @Test
    public void testGetCurrentUserId() {
        // 1.测试当前登录id为空
        String currentUserId = ContextUtil.getCurrentUserId();
        assertNotNull(currentUserId);
        // 2.测试id是否是登录用户的id
        assertEquals(user.getId(), currentUserId);
    }

    @BeforeEach
    @Override
    public void init() {
        MockitoAnnotations.openMocks(this);
        when(user.getId()).thenReturn("1");
    }

    @AfterEach
    @Override
    public void tearDown() {
        user = null;
    }
}
