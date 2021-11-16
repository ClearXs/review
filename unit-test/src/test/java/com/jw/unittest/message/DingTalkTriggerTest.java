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
 * @author jw
 * @date 2021/11/13 11:14
 */
public class DingTalkTriggerTest extends BaseTest {

    @Spy
    DingTalkTrigger trigger;

    /**
     * 因为没有业务属性，所以只能调用方法来查看结果
     */
    @Test
    public void testDingTalkTrigger() {
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
