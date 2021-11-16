package com.jw.basics.thread.lock;

public class Join {


    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + ": " + "执行前");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + ": " + "执行后");
            }
        }, "test");
        thread.start();
        // 那个线程调用了这个方法，main线程调用
        thread.join();
        System.out.println("退出");
    }
}
