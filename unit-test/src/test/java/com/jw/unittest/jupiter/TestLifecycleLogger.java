package com.jw.unittest.jupiter;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public interface TestLifecycleLogger {

    Logger logger = LoggerFactory.getLogger(TestLifecycleLogger.class);

    @BeforeAll
    default void beforeAllTests() {
        logger.info("beforeAllTests");
    }

    @BeforeEach
    default void eachOneBeforeTests() {
        logger.info("eachOneBeforeTests");
    }

    @AfterEach
    default void eachOneAfterTests() {
        logger.info("eachOneAfterTests");
    }

    @AfterAll
    default void afterAllTests() {
        logger.info("afterAllTests");
    }

}
