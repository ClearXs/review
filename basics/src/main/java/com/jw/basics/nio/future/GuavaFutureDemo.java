package com.jw.basics.nio.future;

import com.google.common.util.concurrent.*;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.*;

/**
 * 使用guava实现异步回调
 * @author jiangw
 * @date 2020/11/16 21:30
 * @since 1.0
 */
@Slf4j
public class GuavaFutureDemo {

    static class HotWater implements Callable<Boolean> {

        @Override
        public Boolean call() throws Exception {
            log.info("烧水");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
            log.info("烧水完毕");
            return true;
        }
    }

    static class Wash implements Callable<Boolean> {

        @Override
        public Boolean call() throws Exception {
            log.info("洗碗");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                return false;
            }
            log.info("洗碗完毕");
            return true;
        }
    }

    static class MainJob implements Runnable {

        public boolean isWash = false;

        public boolean isHot = false;

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                    log.info("等待..");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isWash && isHot) {
                    log.info("喝茶");
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MainJob mainJob = new MainJob();
        // 主线程循环等待
        Thread thread = new Thread(mainJob);
        thread.start();
        // 1.创建异步任务
        FutureTask<Boolean> hotTask = new FutureTask<>(new HotWater());
        FutureTask<Boolean> washTask = new FutureTask<>(new Wash());
        // 2.创建线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        // 3.构建Guava线程池
        ListeningExecutorService guavaExecutor = MoreExecutors.listeningDecorator(executor);
        // 4.提交任务，并绑定异步回调
        ListenableFuture<?> hotFuture = guavaExecutor.submit(hotTask);
        Futures.addCallback(hotFuture, new FutureCallback<Object>() {
            @Override
            public void onSuccess(@Nullable Object result) {
                mainJob.isHot = true;
            }

            @Override
            public void onFailure(Throwable t) {
                log.info("失败");
            }
        }, guavaExecutor);
        ListenableFuture<?> washFuture = guavaExecutor.submit(washTask);
        Futures.addCallback(washFuture, new FutureCallback<Object>() {
            @Override
            public void onSuccess(@Nullable Object result) {
                mainJob.isWash = true;
            }

            @Override
            public void onFailure(Throwable t) {
                log.info("错误");
            }
        }, guavaExecutor);
        Thread.sleep(20000);
        guavaExecutor.shutdownNow();
    }
}
