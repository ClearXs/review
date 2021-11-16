package com.jw.basics.netty.rpc;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.*;

/**
 * 请求池，作用如下
 * 1.存放所有的请求
 * 2.与某一个请求有关的响应来时，返回响应，并把对应的请求移除
 * 3.池的缓存为一个map结构
 * 4.采用线程池不断轮询，应该采用cached线程池，这种大量的请求采取非核心线程池
 * 5.若在线程回收还未有响应来到，则直接丢弃这个请求。
 * @author jiangw
 * @date 2020/11/23 14:44
 * @since 1.0
 */
@Slf4j
public class RpcRequestPool {

    private static Map<String, RpcRequest> rpcRequestCached = new ConcurrentHashMap<>();

//    private static ExecutorService executor = new ThreadPoolExecutor(1, Integer.MAX_VALUE, 30, TimeUnit.SECONDS, new SynchronousQueue<>(), new RpcRequestThread());

    private static ExecutorService executor = Executors.newCachedThreadPool();

    private static final Object lock = new Object();

    private static volatile RpcResponse rpcResponse;

    static class RpcRequestThread implements ThreadFactory {

        @Override
        public Thread newThread(Runnable r) {
            ThreadGroup threadGroup = Thread.currentThread().getThreadGroup();
            String name = "RPC-REQUEST-" + Math.random() * Integer.MAX_VALUE;
            return new Thread(threadGroup, name);
        }
    }

    static class RequestTask implements Callable<RpcResponse> {

        private final RpcRequest rpcRequest;

        public RequestTask(RpcRequest rpcRequest) {
            this.rpcRequest = rpcRequest;
        }

        @Override
        public RpcResponse call() throws Exception {
            RpcResponse response = null;
            try {
                synchronized (lock) {
                    while (response == null) {
                        if (rpcResponse == null) {
                            // 阻塞直至响应来到，否则
                            lock.wait();
                        }
                        if (rpcRequestCached.get(rpcResponse.getRequestId()) == rpcRequest) {
                            response = rpcResponse;
                            rpcResponse = null;
                        } else {
                            lock.wait();
                        }
                    }
                }
            } catch (InterruptedException e) {
                log.info("线程, {}：执行超时。{}", Thread.currentThread().getName(), e.getMessage());
            }
            return response;
        }
    }

    public static RpcResponse add(RpcRequest rpcRequest) throws ExecutionException, InterruptedException {
        rpcRequestCached.put(rpcRequest.getRequestId(), rpcRequest);
        Future<RpcResponse> future = executor.submit(new RequestTask(rpcRequest));
        return future.get();
    }

    public static void notify(RpcResponse rpcResponse) {
        // 阻塞直至响应被消费掉
        while (RpcRequestPool.rpcResponse != null) {
        }
        synchronized (lock) {
            RpcRequestPool.rpcResponse = rpcResponse;
            lock.notifyAll();
        }
    }
}
