package com.jw.unittest.mockito;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class MockitoDemo {

    @Mock
    private ThreadLocalRandom random;

    @BeforeEach
    void initAll() {
        // 使当前测试类的@Mock、@Spy注解生效
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test() {
        when(random.nextInt()).thenReturn(100);
        assertEquals(100, random.nextInt());
    }

    @Test
    public void testDefault() {
        ArrayList mock = mock(ArrayList.class);
        // mock对象写方法无效
        mock.add("name");
        // 没有指定size方法返回值所以是默认值即0
        assertEquals(0, mock.size());
        // 同理，默认值也是null
        assertEquals(null, mock.get(0));

        when(mock.size()).thenReturn(1);
        when(mock.get(0)).thenReturn("name");
        // 指定了返回值所以下面判断是正确的
        assertEquals(1, mock.size());
        assertEquals("name", mock.get(0));
    }
}
