package com.jw.unittest.mockito;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class FirstMockDemo {

    static List mockList;

    @BeforeAll
    static void initAll() {
        mockList = mock(ArrayList.class);
    }

    @Test
    public void whenInvokeSomeMethodReturnSomeValue() {
//        // 当调用add时，他一定返回为true
//        when(mockList.add("one")).thenReturn(true);
        // 其他写法
//        doReturn(true).when(mockList).add("one");
        assertTrue(mockList.add("one"));

        // 当调用size时，返回100
        when(mockList.size()).thenReturn(100);
        // 其他写法
//        doReturn(100).when(mockList).size();
        assertEquals(100, mockList.size());

        // 调用get(1)时，返回zhang3
        when(mockList.get(1)).thenReturn("zhang3");
        // 其他写法
//        doReturn("zhang3").when(mockList).get(1);
        assertEquals("zhang3", mockList.get(1));
    }

    @Test
    public void whenInvokeSomeMethodThrowException() {
        // 当调用get(0)时，抛出空指针异常
        doThrow(new NullPointerException()).when(mockList).get(0);
        // 或者其他写法
//        when(mockList.get(0))
//                .thenThrow(new NullPointerException());
        assertThrows(NullPointerException.class, () -> mockList.get(0));
    }

    @Test
    public void whenInvokeSomeMethodDoNothing() {
        doNothing()
                .when(mockList).hashCode();
    }
}
