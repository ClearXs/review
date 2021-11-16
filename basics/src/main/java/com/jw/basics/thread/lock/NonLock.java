package com.jw.basics.thread.lock;

public class NonLock {

    public static void out() {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
        }
    }

    public static void main(String[] args) {
        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                out();
            }
        }, "a");
        Thread b = new Thread(new Runnable() {
            @Override
            public void run() {
                out();
            }
        }, "b");
        a.start();
        b.start();
    }

}
