package com.jw.basics.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SimpleThreadPool {

    public static void main(String[] args) {
        int i = 0;
        retry:
        for(;;) {
            System.out.println(i);
            for(;;) {
                i++;
                if (i == 5) {
                    break retry;
                }
                if (i == 3) {
                    continue retry;
                }
            }
        }
        System.out.println(i);

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 5, 100, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        for (int i1 = 0; i1 < 2; i1++) {
            int finalI = i1;
            poolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println(finalI);
                }
            });
        }
    }
}
