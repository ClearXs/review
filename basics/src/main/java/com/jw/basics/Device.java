package com.jw.basics;

public interface Device {

    void turnOn();

    void turnOff();

    default void forceShutdown() {
        System.out.println("强制关闭");
    }
}
