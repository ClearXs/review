package com.jw.unittest.jupiter;

import static org.junit.jupiter.api.Assertions.*;

import com.jw.unittest.util.Calculator;
import com.jw.unittest.vo.Person;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

@Slf4j
public class AssertionsDemo {

    private Person person;

    @BeforeEach
    void init() {
        person = new Person("hub", "doc");
    }

    @Test
    public void simplyAssertions() {
        Assertions.assertEquals(2, Calculator.add(1, 1));
        assertEquals(4, Calculator.multiply(2, 2));
        assertTrue('a' < 'b', () -> "lazy");
    }

    @Test
    public void groupAssertions() {
        // 在一个分组断言中，所有的断言就会被执行并且所有的失败将会一起报告
        assertAll("name",
                () -> assertEquals("hub", person.getFirstName()),
                () -> assertEquals("doc", person.getLastName())
        );
    }

    @Test
    public void dependentAssertions() {
        // 在代码块中，如果断言失败，将跳过同一块中的后续代码。
        assertAll("properties",
                () -> {
                    String firstName = person.getFirstName();
                    // 断言失败，跳过后续代码
                    assertNull(firstName);
                    assertAll("first name",
                            () -> {
                                assertTrue(firstName.startsWith("h"));
                            },
                            () -> {
                                // 报错
                                assertTrue(firstName.endsWith("c"));
                            }
                    );
                },
                () -> {
                    // 对于分组断言，上一个分组失败不影响当前的执行
                    String lastName = person.getLastName();
                    assertNotNull(lastName);
                    assertAll("last name",
                            () -> {
                                assertTrue(lastName.startsWith("d"));
                            },
                            () -> {
                                // 失败
                                assertTrue(lastName.endsWith("r"));
                            }
                    );
                }
        );
    }

    @Test
    public void exceptionTesting() {
        // 断言抛出此异常
        ArithmeticException arithmeticException = assertThrows(ArithmeticException.class, () -> Calculator.devide(1, 0));
        assertEquals("/ by zero", arithmeticException.getMessage());
    }

    @Test
    public void timeoutNotExceeded() {
        // 断言在指定的时间能够完成任务，如果不能完成则断言失败
        assertTimeout(Duration.ofMillis(100), () -> Thread.sleep(50));
    }

    @Test
    public void timeoutNotExceededWithResult() {
        // 断言在指定的时间内完成并返回结果
        String result = assertTimeout(Duration.ofMillis(100), () -> {
            Thread.sleep(50);
            return "ok";
        });
        assertEquals("ok", result);
    }

    @AfterEach
    public void tearDown() {
        // gc
        person = null;
    }
}
