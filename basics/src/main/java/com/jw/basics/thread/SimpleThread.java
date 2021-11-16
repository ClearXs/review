package com.jw.basics.thread;

/**
 * TDD
 * @author jiangw
 * @date 2020/10/24 15:28
 * @since 1.0
 */
public class SimpleThread extends Thread {

    @Override
    public void run() {
        Thread thread = Thread.currentThread();
        String groupName = thread.getThreadGroup().getName();
        String name = thread.getName();
        int priority = thread.getPriority();
        System.out.println("简单线程: " + name + "- 线程组名: " + groupName + "- 优先级" + priority );
    }

    public static void main(String[] args) {
        SimpleThread simpleThread = new SimpleThread();
        simpleThread.start();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getThreadGroup().getName());
            System.out.println("简单匿名类线程");
        }).start();
    }

}
