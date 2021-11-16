package com.jw.unittest.message;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.jw.unittest.BaseTest;
import com.jw.unittest.vo.MessageVo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Map;

/**
 * Todo
 * @author jw
 * @date 2021/11/13 10:32
 */
public class PlatformMessageInvokerTest extends BaseTest {

    @Spy
    PlatformMessageInvoker invoker;

    @Test
    public void testSpiInjectionTrigger() {
        Map<String, MessageTrigger> trggiers = invoker.getTrggiers();
        // 1.测试有消息触发器被发现
        assertNotEquals(0, trggiers.size());
        // 2.测试消息触发器是DingTalk、Sms
        assertNotNull(trggiers.get(SmsTrigger.class.getName()));
        assertNotNull(trggiers.get(DingTalkTrigger.class.getName()));
    }

    /**
     * 测试invoke分支条件，进行分支覆盖测试
     */

    /**
     * 测试当message为空时，调用invoke抛出异常
     */
    @Test
    public void testInvokeMessageNull() {
        assertThrows(NullPointerException.class, () -> invoker.invoke(null));
    }

    /**
     * 测试trigger为空时，调用invoke抛出异常
     */
    @Test
    public void testInvokeTriggerNull() {
        assertThrows(NullPointerException.class, () -> invoker.invoke(MessageVo.of()));
    }

    @BeforeEach
    @Override
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    @Override
    public void tearDown() {

    }
}
