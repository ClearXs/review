package com.jw.basics;

public class AgentTest {

    public static void main(String[] args) throws InterruptedException {
        final AgentTest agentTest = new AgentTest();
        new Thread(new Runnable() {
            @Override
            public void run() {
                agentTest.test1();
            }
        }, "t1").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                agentTest.test2();
            }
        }, "t2").start();
        Thread.sleep(2222);
    }

    public void test1() {
        System.out.println("test1");
        test2();
    }

    public void test2() {
        System.out.println("test2");
        test3();
    }

    public void test3() {
        System.out.println("test3");
    }
}
