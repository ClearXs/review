package com.jw.basics.thread;

public class SimpleVolatile {

    private int a = 0;
    private volatile boolean flag = false;

    public void write() {
        a = 1;
        flag = true;
    }

    public void reader() {
        if (flag) {
            System.out.println(a);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleVolatile simpleVolatile = new SimpleVolatile();
        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                simpleVolatile.write();
            }
        }, "a");

        Thread b = new Thread(new Runnable() {
            @Override
            public void run() {
                simpleVolatile.reader();
            }
        }, "b");
        b.start();
        a.start();

        // volatile变量写操作一定Happens-Before于读操作之前
        Thread.sleep(1000);
    }
}
