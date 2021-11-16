package com.jw.basics.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS 乐观锁
 * 比较并替换
 * 共享资源 shareValue
 * 期待值 exceptedValue
 * 新值
 * 如果当前共享资源等于期待值, 那么进行替换
 *
 * ABA问题 如果共享变量值为A 替换成B后，又被替换成原来A。无法知道它到底变化没，加版本号
 * 自旋问题
 * 单一变量问题
 * @author jiangw
 * @date 2021/6/24 16:02
 * @since 1.0
 */
public class SimpleCAS {

    private volatile static AtomicInteger atomicValue = new AtomicInteger(0);

    /**
     * 共享资源
     */
    private volatile static int value = 0;

    public static void compareAndSet(int expected, int newValue) {
        for (;;) {
            if (atomicValue.compareAndSet(expected, newValue)) {
                break;
            }
        }
    }

    public static void compareAndWarp(int expected, int newValue) {
        if (value == expected) {
            value = newValue;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                compareAndSet(1, 0);
            }
        }, "a");
        Thread b = new Thread(new Runnable() {
            @Override
            public void run() {
                compareAndSet(0, 1);
            }
        }, "b");

        b.start();
        a.start();
        Thread.sleep(500);
        System.out.println(atomicValue.get());
    }
}
