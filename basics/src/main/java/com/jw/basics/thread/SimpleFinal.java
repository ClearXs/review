package com.jw.basics.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <b>
 *     final与线程安全性
 * </b>
 * <p>
 *     final与基本类型的内存语义：
 *     1.写final域规则：JMM禁止编译器把final域所在的上下操作重排序到构造方法之外。通过在return之前插入StoreStore内存屏障
 *     2.读final域规则：JMM不允许读对象引用与读对象引用的final域之间的操作重排序，他会加上LoadLoad内存屏障
 * </p>
 * <p>
 *     final与引用类型的内存语义:
 *     1.在构造方法对一个final域引用的对象进行写入（比如map.put），与随后在构造方法外把这个fianl域读之间不能进行指令重排序
 * </p>
 * @author jiangw
 * @date 2020/11/4 11:31
 * @since 1.0
 */
public class SimpleFinal {

    private int i;

    private final int j;

    private static SimpleFinal simpleFinal;

    public SimpleFinal() {
        i = 1;
        // 写final域规则：JMM禁止编译器把final域所在的上下操作重排序到构造方法之外。通过在return之前插入StoreStore内存屏障
        j = 2;
    }

    private static void write() {
        simpleFinal = new SimpleFinal();
    }

    private static void render() {
        // 正常输出 1， 2
        // 因为final域不允许指令重排序，这就是说普通的变量i的赋值，一定会在构造方法中进行。而不会被重排序到构造方法之外。
        // 读final域规则：JMM不允许读对象引用与读对象引用的final域之间的操作重排序，他会加上LoadLoad内存屏障
        System.out.println(simpleFinal.j);
        System.out.println(simpleFinal.i);
    }

    public static void main(String[] args) {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(2, 2, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
        for (int i = 0; i < 2; i++) {
            if (i == 0) {
                poolExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        write();
                    }
                });
            } else {
                poolExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        render();
                    }
                });
            }
        }
    }
}
