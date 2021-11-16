package com.jw.spring.event;

import org.springframework.context.ApplicationEvent;

public class CustomEvent extends ApplicationEvent {

    private String name;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public CustomEvent(Object source, String name) {
        super(source);
        this.name = name;
    }

    @Override
    public String toString() {
        return "CustomEvent{" +
                "name='" + name + '\'' +
                '}';
    }
}
