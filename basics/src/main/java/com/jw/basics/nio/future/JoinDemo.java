package com.jw.basics.nio.future;

import lombok.extern.slf4j.Slf4j;

/**
 * join测试，调用thread实例方法join，会导致调用线程等待，也可以设置超时等待时间
 * @author jiangw
 * @date 2020/11/16 20:40
 * @since 1.0
 */
@Slf4j
public class JoinDemo {

    static class HotWaterRunnable implements Runnable {

        @Override
        public void run() {
            log.info("烧水");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("烧水完毕");
        }
    }

    static class WashRunnable implements Runnable {

        @Override
        public void run() {
            log.info("洗碗");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("洗碗完毕");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread hot = new Thread(new HotWaterRunnable(), "hot");
        Thread wash = new Thread(new WashRunnable(), "wash");
        log.info("开始泡茶");
        wash.start();
        wash.join(500);
        hot.start();
        hot.join();
        log.info("泡茶完毕");
    }
}
