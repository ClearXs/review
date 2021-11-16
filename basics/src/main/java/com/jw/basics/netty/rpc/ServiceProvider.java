package com.jw.basics.netty.rpc;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 服务器
 * @author jiangw
 * @date 2020/11/22 23:54
 * @since 1.0
 */
public class ServiceProvider {

    /**
     * 服务列表
     * key：api名称
     * value：实现Class
     */
    private final ConcurrentHashMap<String, Class<?>> serviceList;

    public static ServiceProvider provider;

    private static Lock lock;

    /**
     * 单例模式的双重锁
     */
    private ServiceProvider() {
        serviceList = new ConcurrentHashMap<>();
        lock = new ReentrantLock();
    }

    public static ServiceProvider getInstance() {
        // 第一重判断，减少初始化之外所有线程的等待
        if (provider == null) {
            synchronized (ServiceProvider.class) {
                // 第二个判断，避免多个线程等待时，发现第一个线程已经实例化对象，避免多次实例化对象
                if (provider == null) {
                    provider = new ServiceProvider();
                }
            }
        }
        return provider;
    }

    public Class<?> getService(String apiKey) {
        lock.lock();
        try {
            return serviceList.get(apiKey);
        } finally {
            lock.unlock();
        }
    }

    public Class<?> addService(String apiKey, Class<?> realize) {
        lock.lock();
        try {
            return serviceList.putIfAbsent(apiKey, realize);
        } finally {
            lock.unlock();
        }
    }

}
