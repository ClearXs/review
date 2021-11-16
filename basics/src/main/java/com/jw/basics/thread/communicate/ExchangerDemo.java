package com.jw.basics.thread.communicate;

import java.util.concurrent.Exchanger;

public class ExchangerDemo {

    public static void main(String[] args) throws InterruptedException {
        Exchanger<String> exchanger = new Exchanger<>();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("线程A，等待线程B的数据是: " + exchanger.exchange("线程A的数据"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("线程B，等待线程A的数据是: " + exchanger.exchange("线程B的数据"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("线程C，等待线程的数据是: " + exchanger.exchange("线程C的数据"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("线程D，等待线程的数据是: " + exchanger.exchange("线程D的数据"));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Thread.currentThread().join();
    }
}
