package com.jw.unittest.jupiter;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class RepeatedTestsDemo {

    @RepeatedTest(10)
    public void repetaedTest() {
        // 每次测试都会创建一个新的实例
    }
}
