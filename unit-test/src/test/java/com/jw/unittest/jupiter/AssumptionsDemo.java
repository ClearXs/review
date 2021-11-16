package com.jw.unittest.jupiter;

import com.jw.unittest.util.Calculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assumptions.*;
import static org.junit.jupiter.api.Assertions.*;

public class AssumptionsDemo {

    @Test
    public void testOnlyCiServer() {
        assumeTrue("CI".equals(System.getenv("ENV")));
        // 继续执行单测
    }

    @Test
    public void testOnlyDeveloperWorkstation() {
        assumeTrue("DEV".equals(System.getenv("ENV")),
                () -> "abort test, not developer workstation");
        // 继续执行单测
    }

    @Test
    public void testAllEnvironments() {
        assumingThat("CI".equals(System.getenv("ENV")),
                () -> {
                    // 只有在CI环境下才执行这个断言
                    Assertions.assertEquals(2, Calculator.devide(4, 2));
                }
        );
        assertEquals(42, Calculator.multiply(6, 7));
    }

}
