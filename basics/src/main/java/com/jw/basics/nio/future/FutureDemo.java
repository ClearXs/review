package com.jw.basics.nio.future;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@Slf4j
public class FutureDemo {

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

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Boolean> hotTask = new FutureTask<>(new HotWater());
        FutureTask<Boolean> washTask = new FutureTask<>(new Wash());
        Thread hotThread = new Thread(hotTask);
        Thread washThread = new Thread(washTask);
        hotThread.start();
        washThread.start();
        Boolean hot = hotTask.get();
        Boolean wash = washTask.get();
        if (hot && wash) {
            log.info("完成");
        }
    }
}
