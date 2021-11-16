package com.jw.basics.thread.communicate;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo {

    static class MyRunnable implements Runnable {

        private int value;

        private Semaphore semaphore;

        public MyRunnable(int value, Semaphore semaphore) {
            this.value = value;
            this.semaphore = semaphore;
        }

        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println(String.format("当前线程是%d, 还剩下%d个资源，还有%d线程阻塞",
                        value,
                        semaphore.availablePermits(),
                        semaphore.getQueueLength()));
                semaphore.release();
                Random random = new Random();
                int randomNumber = random.nextInt(1000);
                Thread.sleep(randomNumber);
                System.out.println(String.format("线程%d，释放资源", value));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 6; i++) {
            new Thread(new MyRunnable(i, semaphore)).start();
        }
    }
}
