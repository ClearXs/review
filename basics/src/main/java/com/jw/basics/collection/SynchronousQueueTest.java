package com.jw.basics.collection;

import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueTest {

    public static void main(String[] args) throws InterruptedException {
        // put操作阻塞直至take
        // 公平与非公平模式
        // 公平模式下，内部数据结构为队列（FIFO）
        // 非公平模式下，内部数据结构为栈（FILO）
        SynchronousQueue<String> queue = new SynchronousQueue<>(false);

        Thread put1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("put1 data");
                    queue.put("1");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("end put1");
            }
        });

        Thread put2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("put2 data");
                    queue.put("2");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("end put2");
            }
        });

        Thread take = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("take data");
                    String result = queue.take();
                    System.out.println("take: " + result);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        put1.start();
        put2.start();

        Thread.sleep(1000);

        take.start();
    }
}
