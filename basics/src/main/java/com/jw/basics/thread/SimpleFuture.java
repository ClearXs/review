package com.jw.basics.thread;

import java.util.concurrent.*;

public class SimpleFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(1, 1, 300, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(1));
        FutureTask<String> futureTask = new FutureTask<>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "13";
            }
        });

        poolExecutor.execute(futureTask);

        Future<String> submit = poolExecutor.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(10000);
                return "test";
            }
        });
        String s = futureTask.get();
        String s1 = submit.get();
        System.out.println(s);
        System.out.println(s1);
    }
}
