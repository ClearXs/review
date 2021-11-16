package com.jw.basics.thread.pool;

import java.util.concurrent.*;

public class RejectedExecutionHandler {

    public static void main(String[] args) throws InterruptedException {
        // 默认拒绝执行处理器有4种
        // DiscardPolicy -- 默认丢弃进来的任务
        // AbortPolicy   -- 抛出RejectedExecutionException异常
        // DiscardOldestPolicy -- 丢弃最老的任务，执行当前进来的任务
        // CallerRunsPolicy    -- 在ThreadPoolExecutor调度线程中执行任务(或者说由主线程，这里是main来调用)
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1,
                1,
                1000,
                TimeUnit.MICROSECONDS,
                new LinkedBlockingDeque<>(),
                new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 1000; i++) {
            int finalI = i;
            try {
                poolExecutor.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName() + "执行: " + finalI);
                    }
                });
            } catch (RejectedExecutionException e) {
                e.printStackTrace();
            }
        }
        Thread.currentThread().join();
    }
}
