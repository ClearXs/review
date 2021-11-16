package com.jw.unittest.jupiter;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

@Slf4j
public class StandardTests {

    @BeforeAll
    static void initAll() {
        log.info("init All");
    }

    @BeforeEach
    void init() {
        log.info("init each one");
    }

    @Test
    void failingTest() {
        fail("failing test");
    }

    @Test
    @Disabled("demonstration purposes")
    void skippedTest() {

    }

    @Test
    void abortedTest() {
        assumeTrue("abc".equals("Z"));
        fail("aborted");
    }

    @AfterEach
    void tearDown() {
        log.info("tear down");
    }

    @AfterAll
    static void tearDownAll() {
        log.info("tear down all");
    }
}
