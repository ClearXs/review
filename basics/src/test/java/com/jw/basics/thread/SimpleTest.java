package com.jw.basics.thread;

import com.jw.basics.SimpleEnum;
import com.jw.basics.thread.handle.SimpleExceptionHandler;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class SimpleTest {

    @Test
    public void testThread() {
        IntStream.range(1, 10).forEach(i -> {
            Thread thread = new Thread(new SimpleThread());
            thread.setPriority(i);
            thread.start();
        });

        ThreadGroup threadGroup = new ThreadGroup("test");
        threadGroup.setMaxPriority(4);
        Thread thread = new Thread(threadGroup, "test-1");
        thread.setPriority(5);
        thread.start();
    }

    @Test
    public void testThreadStatus() {
        Thread thread = new Thread(new SimpleThread());
        Thread.State state = thread.getState();
        thread.start();
        state = thread.getState();
        String name = state.name();
        System.out.println(name);
        SimpleEnum anEnum = SimpleEnum.getEnum(1);
        System.out.println(anEnum.name());
    }

    @Test
    public void blockedTest() throws InterruptedException {
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    testMethod();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "a");
        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    testMethod();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "b");
        threadA.start();
        Thread.sleep(1000);
        threadB.start();
        System.out.println(threadA.getName() + ": " + threadA.getState());
        System.out.println(threadB.getName() + ": " + threadB.getState());
    }

    @Test
    public void waiting() throws InterruptedException {
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    testMethod();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "a");
        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    testMethod();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ;
            }
        }, "b");
        threadA.start();
        threadA.join(500);
        threadB.start();
        Thread.sleep(1000);
        System.out.println(threadA.getName() + ": " + threadA.getState());
        System.out.println(threadB.getName() + ": " + threadB.getState());
    }

    @Test
    public void testIntercept() throws InterruptedException {
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean interrupted = Thread.currentThread().isInterrupted();
                System.out.println(interrupted);
            }
        }, "a");
        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    testMethod();
                } catch (InterruptedException e) {
                    boolean interrupted = Thread.currentThread().isInterrupted();
                    System.out.println(interrupted);
                    e.printStackTrace();
                }
            }
        }, "b");
        threadB.start();
        TimeUnit.MILLISECONDS.sleep(500);
        threadA.start();
        TimeUnit.MILLISECONDS.sleep(200);
        System.out.println(threadA.getName() + ": " + threadA.getState());
        System.out.println(threadB.getName() + ": " + threadB.getState());
        threadA.interrupt();
        threadB.interrupt();
        TimeUnit.MILLISECONDS.sleep(200);
        TimeUnit.SECONDS.sleep(1);
    }

    private synchronized void testMethod() throws InterruptedException {
        Thread.sleep(1000);
    }


    @Test
    public void testInterrupted() {
        Thread thread = Thread.currentThread();
        thread.interrupt();
        boolean interrupted = Thread.interrupted();
        boolean interrupted1 = thread.isInterrupted();
        System.out.println(interrupted1);
        System.out.println(interrupted);
        interrupted = Thread.interrupted();
        System.out.println(interrupted);
    }

    @Test
    public void test() {
        boolean is = true;
        boolean o = is || false;
    }

    @Test
    public void test22() {
        BigDecimal bigDecimal = new BigDecimal("1.1");
        bigDecimal = bigDecimal.add(new BigDecimal("0.1"));
        System.out.println(bigDecimal.toString());
    }

    @Test
    public void testException() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(1 / 0);
            }
        });
        thread.setUncaughtExceptionHandler(new SimpleExceptionHandler());
        thread.start();
    }

}
