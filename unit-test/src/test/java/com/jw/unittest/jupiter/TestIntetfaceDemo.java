package com.jw.unittest.jupiter;

import com.jw.unittest.util.Calculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestIntetfaceDemo implements TestLifecycleLogger {

    @Test
    public void equalsValue() {
        Assertions.assertEquals(1, Calculator.multiply(1, 1));
    }
}
