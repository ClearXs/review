package com.jw.basics.thread;

import lombok.SneakyThrows;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleCompletableFuture {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Thread.sleep(10000);
                completableFuture.complete("32");
            }
        });
        String s = completableFuture.get();
        System.out.println(s);
    }
}
