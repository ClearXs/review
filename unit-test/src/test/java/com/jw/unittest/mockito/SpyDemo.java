package com.jw.unittest.mockito;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.List;

public class SpyDemo {

    static class CalculateService {

        public int add(int a, int b) {
            return a + b;
        }
    }

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSpy() {
        CalculateService spy = spy(new CalculateService());
        // 默认走真实方法
        assertEquals(4, spy.add(1, 3));

        // stub之后将不会走真实方法
        when(spy.add(1, 3)).thenReturn(10);
        assertEquals(10, spy.add(1, 3));
        
    }

    @Spy
    private List<String> list;

    @Test
    public void testSpyAnnotated() {
        list.add("1");
        assertEquals("1", list.get(0));
    }
}
