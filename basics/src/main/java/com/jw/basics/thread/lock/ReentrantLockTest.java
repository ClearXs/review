package com.jw.basics.thread.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        ReentrantLock reentrantLock = new ReentrantLock();
        for (int i = 0; i < 5; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    reentrantLock.lock();
                    try {
                        System.out.println(Thread.currentThread().getName());
                    } finally {
                        reentrantLock.unlock();
                    }
                }
            });
        }
    }
}
