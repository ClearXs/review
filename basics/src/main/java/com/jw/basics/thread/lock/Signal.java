package com.jw.basics.thread.lock;

public class Signal {

    /**
     * 内存可见性
     */
    private static volatile int signal = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                while (signal < 5) {
                    if (signal % 2 == 0) {
                        System.out.println(Thread.currentThread().getName() + ": " + signal);
                        synchronized (this) {
                            signal++;
                        }
                    }
                }
            }
        }, "a");
        Thread b = new Thread(new Runnable() {
            @Override
            public void run() {
                while (signal < 5) {
                    if (signal % 2 == 1) {
                        System.out.println(Thread.currentThread().getName() + ": " + signal);
                        synchronized (this) {
                            signal++;
                        }
                    }
                }
            }
        }, "b");
        a.start();
        Thread.sleep(100);
        b.start();
    }
}
