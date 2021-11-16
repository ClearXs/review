package com.jw.basics.thread.lock;

import java.util.concurrent.locks.StampedLock;

/**
 * 无锁编程的{@link java.util.concurrent.locks.StampedLock}
 * @author jiangw
 * @date 2020/12/1 13:48
 * @since 1.0
 */
public class RWStampedLock {



    class Point {
        private double x, y;
        private final StampedLock stampedLock = new StampedLock();

        // 写锁的使用
        void move(double deltaX, double deltaY) {
            long stamp = stampedLock.writeLock();
            try {
                x += deltaX;
                y += deltaY;
            } finally {
                stampedLock.unlockWrite(stamp);
            }
        }

        double distanceFromOrigin() {
            // 获取乐观读锁
            long stamp = stampedLock.tryOptimisticRead();
            double currentX = x, currentY = y;
            // 检查乐观读锁是否有其他写锁发生，有则返回false
            if (!stampedLock.validate(stamp)) {
                // 获取悲观读锁
                stamp = stampedLock.readLock();
                try {
                    currentX = x;
                    currentY = y;
                } finally {
                    stampedLock.unlockRead(stamp);
                }
            }
            return Math.sqrt(currentX * currentX + currentY * currentY);
        }

        // 悲观读锁以及读锁升级写锁的过程
        void moveIfAtOrigin(double newX, double newY) {
            // 悲观读锁
            long stamp = stampedLock.readLock();
            try {
                while (x == 0.0 && y == 0.0) {
                    // 尝试转换为写锁，如果转换失败，说明有其他的线程占有写锁
                    long ws = stampedLock.tryConvertToWriteLock(stamp);
                    if (ws != 0L) {
                        stamp = ws;
                        x = newX;
                        y = newY;
                        break;
                    } else {
                        // 转换失败，强行获取写锁
                        stampedLock.unlockRead(stamp);
                        stamp = stampedLock.writeLock();
                    }
                }
            } finally {
                // 释放所有锁
                stampedLock.unlock(stamp);
            }
        }
    }
}
