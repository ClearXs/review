package com.jw.basics.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleThreadLocal {

    private final static ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        THREAD_LOCAL.set("22121");
        System.out.println(Thread.currentThread().getName() + "获取ThreadLocal值：" + THREAD_LOCAL.get());
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "获取ThreadLocal值：" + THREAD_LOCAL.get());
                THREAD_LOCAL.set("2111");
                System.out.println(Thread.currentThread().getName() + "获取ThreadLocal值：" + THREAD_LOCAL.get());
            }
        });
        Thread.currentThread().join();
    }
}
