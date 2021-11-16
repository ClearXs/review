package com.jw.basics.thread.lock;

import java.util.concurrent.TimeUnit;

public class SimpleLock {

    private static final Object LOCK = new Object();

    public static void out() {
        synchronized (LOCK) {
            for (int i = 0; i < 100; i++) {
                System.out.println(Thread.currentThread().getName() + ": " + i);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
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
        TimeUnit.MILLISECONDS.sleep(100);
        b.start();
    }
}
