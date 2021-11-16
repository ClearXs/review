package com.jw.basics.thread.communicate;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Todo
 * @author jw
 * @date 2021/11/15 22:58
 */
public class CyclicBarrierDemo {


    public static void main(String[] args) {
        CyclicBarrier cb = new CyclicBarrier(3, () -> {
            // 当所有线程到达checkpoint时，触发
            System.out.println("关卡加载完成");
        });
        new Thread(new Checkpoint("地图数据", cb)).start();
        new Thread(new Checkpoint("人物模型", cb)).start();
        new Thread(new Checkpoint("背景音乐", cb)).start();
    }

    static class Checkpoint implements Runnable {

        private String task;

        private CyclicBarrier cb;

        public Checkpoint(String task, CyclicBarrier cb) {
            this.task = task;
            this.cb = cb;
        }

        @Override
        public void run() {

            try {
                for (int i = 0; i < 3; i++) {
                    // 显示当前任务关卡加载进度
                    System.out.println(String.format(Thread.currentThread().getName() + "关卡%d完成%s加载", i, task));
                    cb.await();
                }
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
            cb.reset();
        }
    }
}
