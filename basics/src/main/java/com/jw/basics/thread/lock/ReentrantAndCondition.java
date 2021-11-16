package com.jw.basics.thread.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantAndCondition {

    private static volatile boolean execute = false;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        final ReentrantLock lock = new ReentrantLock();
        final Condition condition = lock.newCondition();
        for (int i = 0; i < 6; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    lock.lock();
                    try {
                        if (!execute) {
                            long l = condition.awaitNanos(TimeUnit.SECONDS.toNanos(3));
                            if (l <= 0) {
                                System.out.println(Thread.currentThread().getName() + " lock condition");
                            } else {
                                System.out.println(Thread.currentThread().getName() + " started execute");
                            }
                        } else {
                            System.out.println(Thread.currentThread().getName() + " execute...");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            });
        }

        TimeUnit.SECONDS.sleep(2);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    System.out.println("release condition");
                    execute = true;
                    condition.signalAll();
                } finally {
                    lock.unlock();
                }
            }
        });
    }
}
