package com.jw.springbootactuator.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

@Component
public class DemoHealthIndicator extends AbstractHealthIndicator {

    public DemoHealthIndicator() {
        System.out.println("21");
    }

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {

        builder.up().build();

        builder.down().withDetail("msg", "错误信息");
    }
}
