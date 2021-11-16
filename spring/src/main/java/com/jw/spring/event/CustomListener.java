package com.jw.spring.event;

import org.springframework.context.ApplicationListener;

public class CustomListener implements ApplicationListener<CustomEvent> {

    @Override
    public void onApplicationEvent(CustomEvent event) {
        System.out.println(event);
    }
}
