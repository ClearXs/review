package com.jw.basics.thread.lock;

public class WaitAndNotify {

    private static final Object LOCK = new Object();

    public static void out() {
        synchronized (LOCK) {
            for (int i = 0; i < 5; i++) {
                try {
                    System.out.println(Thread.currentThread().getName() + ": " + i);
                    LOCK.notify();
                    LOCK.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
        Thread.sleep(100);
        b.start();
    }
}
