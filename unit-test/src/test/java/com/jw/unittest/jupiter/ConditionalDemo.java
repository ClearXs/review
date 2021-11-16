package com.jw.unittest.jupiter;

import com.jw.unittest.util.Calculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

@DisplayName("ConditionalDemo123")
public class ConditionalDemo {

    @Test
    @EnabledOnOs(OS.LINUX)
    public void enableMac() {
        Assertions.assertEquals(2, Calculator.multiply(1, 3));
    }
}
