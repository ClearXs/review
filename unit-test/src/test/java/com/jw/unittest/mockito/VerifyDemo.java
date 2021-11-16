package com.jw.unittest.mockito;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class VerifyDemo {

    @Mock
    private List<String> list;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test() {
        list.add("1");
        // 检验list是否调用过add
        verify(list).add("1");

        // 检验list是否调用过add 2此
        verify(list, times(2)).add("1");
    }
}
