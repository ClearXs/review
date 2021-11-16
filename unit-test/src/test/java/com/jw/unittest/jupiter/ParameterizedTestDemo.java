package com.jw.unittest.jupiter;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@Slf4j
public class ParameterizedTestDemo {

    @ParameterizedTest
    @ValueSource(strings = {"test1", "test2"}, ints = {1, 2})
    public void testInjection(String candidate, int num) {
        log.info(candidate + " and " + num);
        Assertions.assertEquals("test1", candidate);
    }

    @ParameterizedTest
    @CsvSource({
            "zhang3, 1",
            "li4, 2"
    })
    public void multiParameterInjection(String name, int count) {
        log.info(name + " and " + count);
    }
}
