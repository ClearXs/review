package com.jw.unittest.message;

import com.jw.unittest.BaseTest;
import com.jw.unittest.vo.MessageVo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

/**
 * Todo
 *
 * @author jw
 * @date 2021/11/13 11:14
 */
public class SmsTriggerTest extends BaseTest {

    @Spy
    SmsTrigger trigger;

    /**
     * 无具体业务逻辑，所以粒度只能调用方法来看结果
     */
    @Test
    public void testSmsTrigger() {
        trigger.run(MessageVo.of());
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
