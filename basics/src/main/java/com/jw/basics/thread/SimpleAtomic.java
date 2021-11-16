package com.jw.basics.thread;


import java.util.concurrent.atomic.AtomicInteger;

public class SimpleAtomic {

    public static void main(String[] args) throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger(2);
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    atomicInteger.getAndAdd(finalI);
                }
            }).start();
        }
        Thread.sleep(500);
        System.out.println(atomicInteger.get());
    }
}
