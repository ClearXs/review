package com.jw.basics.thread.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadAndWriteLock {

    private volatile boolean isRead = false;

    ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    public void testReentrantReadWriteLock() {
        Thread writeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                write();
            }
        });

        Thread readThread = new Thread(new Runnable() {
            @Override
            public void run() {
                read();
            }
        });

        readThread.start();
        writeThread.start();
    }

    private void write() {
        try {
            reentrantReadWriteLock.readLock().lock();
            if (!isRead) {
                reentrantReadWriteLock.readLock().unlock();
                reentrantReadWriteLock.writeLock().lock();
                isRead = true;

                reentrantReadWriteLock.readLock().lock();
            }
        } finally {
            reentrantReadWriteLock.writeLock().unlock();
        }
    }

    private void read() {
        try {
            reentrantReadWriteLock.readLock().lock();
            while (!isRead) {
                System.out.println("read blocked");
            }
            System.out.println(isRead);
        } finally {
            reentrantReadWriteLock.readLock().unlock();
        }
//        while (!isRead) {
//            System.out.println("read blocked");
//        }
//        System.out.println(isRead);
    }

    public static void main(String[] args) {
        ReadAndWriteLock readAndWriteLock = new ReadAndWriteLock();
        readAndWriteLock.testReentrantReadWriteLock();
    }
}
