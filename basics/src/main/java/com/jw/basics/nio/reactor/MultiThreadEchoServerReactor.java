package com.jw.basics.nio.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多线程的反应器，引入多个反应器，每个反应器对应一个线程IO注册事件，也就引入子反应器
 * 子反应器负责每个IO事件的查询，处理。
 * @author jiangw
 * @date 2020/11/15 22:30
 * @since 1.0
 */
public class MultiThreadEchoServerReactor {

    private final ServerSocketChannel serverSocketChannel;

    private final AtomicInteger selectorIndex = new AtomicInteger(0);

    /**
     * 选择器
     */
    private final Selector[] selectors = new Selector[2];

    /**
     * 子反应器负责一个选择器IO事件的查询，请求或读写处理。但一定有一个反应器是同时负责请求与读写处理的。
     */
    private final SubReactor[] subReactors = new SubReactor[2];

    /**
     * 处理子选择的线程池。
     */
    private final ExecutorService executor = new ThreadPoolExecutor(2, 2, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

    public MultiThreadEchoServerReactor() throws IOException {
        // 初始化服务器
        this.selectors[0] = Selector.open();
        this.selectors[1] = Selector.open();
        this.serverSocketChannel = ServerSocketChannel.open();
        // 1.通道绑定地址
        serverSocketChannel.socket().bind(new InetSocketAddress("localhost", 8080));
        // 2.设置非阻塞模式
        serverSocketChannel.configureBlocking(false);
        // 3.当前通道注册到选择器上，并获取选择键。
        SelectionKey selectionKey = serverSocketChannel.register(this.selectors[0], SelectionKey.OP_ACCEPT);
        // 4.向选择键中添加handler，作为回调对象
        selectionKey.attach(new AcceptHandler());
        // 5.创建子选择器，处理请求
        this.subReactors[0] = new SubReactor(this.selectors[0]);
        this.subReactors[1] = new SubReactor(this.selectors[1]);
    }

    public void start() {
        for (SubReactor subReactor : subReactors) {
            executor.execute(subReactor);
        }
    }

    public void shutdown() throws IOException {
        executor.shutdown();
        serverSocketChannel.close();
    }

    static class SubReactor implements Runnable {

        private final Selector selector;

        public SubReactor(Selector selector) {
            this.selector = selector;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    // 阻塞，直到有感兴趣的事件
                    this.selector.select();
                    Set<SelectionKey> selectionKeys = this.selector.selectedKeys();
                    for (SelectionKey selectionKey : selectionKeys) {
                        dispatch(selectionKey);
                    }
                    // 只处理一个
                    selectionKeys.clear();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        private void dispatch(SelectionKey selectionKey) {
            Runnable handler = (Runnable) selectionKey.attachment();
            if (handler != null) {
                handler.run();
            }
        }

    }

    class AcceptHandler implements Runnable {

        @Override
        public void run() {
            // 接收请求处理器，创建一个IOHandler
            try {
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    // 选择一个选择器作为处理读写等请求，或许是处理请求的选择器，或许不是。
                    new EchoHandler(socketChannel, selectors[selectorIndex.get()]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (selectorIndex.incrementAndGet() == selectors.length) {
                selectorIndex.set(0);
            }
        }
    }
}
