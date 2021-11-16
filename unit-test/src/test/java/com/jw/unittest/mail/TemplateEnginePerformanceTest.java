package com.jw.unittest.mail;

import com.jw.unittest.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TemplateEnginePerformanceTest extends BaseTest  {

    TemplateEngine templateEngine;

    @BeforeEach
    @Override
    public void init() {
        List<String> variables = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            variables.add("a" + i);
        }
        String template = variables.stream().reduce("", (a, b) -> {
            if (a.equals("")) {
                return "${" + b + "}";
            } else {
                return a + "," + "${" + b + "}";
            }
        });
        templateEngine = new TemplateEngine(template);
        for (int i = 0; i < variables.size(); i++) {
            templateEngine.set(variables.get(i), Integer.toString(i));
        }
    }

    @Test
    void test100WordsVariables() {
        Assertions.assertTimeout(Duration.ofMillis(50), () -> {
            templateEngine.evaluate();
        });
    }

    @Override
    public void tearDown() {
        templateEngine = null;
    }
}
