package com.jw.basics;

public class Computer implements Device {
    @Override
    public void turnOn() {

    }

    @Override
    public void turnOff() {

    }

    public static void main(String[] args) {
        Computer computer = new Computer();
        computer.forceShutdown();
    }
}
