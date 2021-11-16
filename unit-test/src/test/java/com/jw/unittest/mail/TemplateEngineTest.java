package com.jw.unittest.mail;

import com.jw.unittest.BaseTest;
import com.jw.unittest.mail.exception.MissingValueException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * TDD完成邮件模板引擎功能
 * 需求可能如下：
 * 1.系统可以使用运行时提供的变量值替换模板中的变量，如${firstname}和${lastname}；
 * 2.若系统试图发送一个变量赋值尚未完全的模板，应该报错；
 * 3.系统会忽略模板中不存在的变量值；
 *
 * @author jw
 * @date 2021/11/15 14:39
 */
public class TemplateEngineTest extends BaseTest {

    TemplateEngine template;

    @BeforeEach
    @Override
    public void init() {
        template = new TemplateEngine("${one},${two},${three}");
        template.set("one", "1");
        template.set("two", "2");
        template.set("three", "3");
    }

    /**
     * 测试多变量赋值
     */
    @Test
    void testMultipleVariables() {
        Assertions.assertEquals("1,2,3", template.evaluate());
    }

    /**
     * 测试未知的变量是否被忽略掉
     */
    @Test
    void testUnknownVariableAreIgnored() {
        template.set("exist", "no");
        Assertions.assertEquals("1,2,3", template.evaluate());
    }

    @Test
    void testMissingValueRaisesException() {
        Assertions.assertThrows(MissingValueException.class, () -> {
            new TemplateEngine("${name}").evaluate();
        });
    }

    @AfterEach
    @Override
    public void tearDown() {
        template = null;
    }
}
