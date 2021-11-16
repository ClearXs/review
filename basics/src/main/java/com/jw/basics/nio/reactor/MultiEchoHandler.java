package com.jw.basics.nio.reactor;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class MultiEchoHandler implements Runnable {

    /**
     * 客户端通断
     */
    private final SocketChannel socketChannel;

    /**
     * 客户端通断注册后获取的选择键，用于把当前对象放入选择键的attachment中
     */
    private final SelectionKey selectionKey;

    /**
     * 读写缓存
     */
    private final ByteBuffer buffer = ByteBuffer.allocate(1024);

    /**
     * 接收，发送标识
     */
    private final static int RECEIVED = 0, SENDING = 1;

    private int handlerStatus = RECEIVED;

    private ExecutorService executor = new ThreadPoolExecutor(2, 4, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

    public MultiEchoHandler(SocketChannel socketChannel, Selector selector) throws IOException {
        this.socketChannel = socketChannel;
        this.socketChannel.configureBlocking(false);
        // 1.将客户端通道注册到selector中，这样可以确保EchoHandler与Reactor是再同一个线程里，因为Reactor中选择其不断监视是否有事件，监听到之后，使用dispatch处理handler
        this.selectionKey = this.socketChannel.register(selector, 0);
        // 2.将handler添加到选择键的attachment中，这样回调的时候可以正确走到run方法里
        this.selectionKey.attach(this);
        // 3.注册OP_READ事件
        this.selectionKey.interestOps(SelectionKey.OP_READ);
        // 4.wakeup可以立即监视到的第一个IO事件返回，也就是上面的OP_READ事件
        selector.wakeup();
    }

    @Override
    public void run() {
        executor.execute(new AsyncTask());
    }

    /**
     * 一个异步任务
     * @throws IOException
     */
    public synchronized void handle() throws IOException {
        // 读取
        if (handlerStatus == RECEIVED) {
            int length = 0;
            while ((length = this.socketChannel.read(buffer)) > 0) {
                log.info("===>服务器接收数据：{}", new String(buffer.array()));
            }
            // 读取完后，进入写模式
            this.buffer.flip();
            this.selectionKey.interestOps(SelectionKey.OP_READ);
            this.handlerStatus = SENDING;
        } else {
            // 写入
            this.socketChannel.write(buffer);
            // 切换为读模式
            this.buffer.clear();
            this.buffer.flip();
            this.selectionKey.interestOps(SelectionKey.OP_WRITE);
            this.handlerStatus = RECEIVED;
        }
    }

    class AsyncTask implements Runnable {

        @Override
        public void run() {
            try {
                MultiEchoHandler.this.handle();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
