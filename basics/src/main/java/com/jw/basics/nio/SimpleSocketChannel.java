package com.jw.basics.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class SimpleSocketChannel {

    private final static String HOSTNAME = "localhost";

    private final static Integer PORT = 8080;

    private static volatile Boolean closable = false;

    public static void main(String[] args) throws IOException, InterruptedException {
        // 客户端非阻塞通道
        // 1.获取SocketChannel
        SocketChannel socketChannel = SocketChannel.open();
        // 2.设置非阻塞通道
        socketChannel.configureBlocking(false);
        // 3.发起连接
        socketChannel.connect(new InetSocketAddress(HOSTNAME, PORT));
        // 4.非阻塞状态下可能与服务端没有建立真正的连接，所以需要自旋询问
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 0, TimeUnit.MICROSECONDS, new LinkedBlockingQueue<>());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // 线程自旋询问连接是否建立
                    boolean isConnect = socketChannel.finishConnect();
                    while (!isConnect) {
                        isConnect = socketChannel.finishConnect();
                    }
                    log.info("===>连接已建立");
                    ByteBuffer buffer = null;
                    try {
                        // 发送数据
                        buffer = ByteBuffer.allocate(1024);
                        buffer.put("test".getBytes());
                        // 读模式
                        buffer.flip();
                        // 发起数据，阻塞发送
                        socketChannel.write(buffer);
                        // 终止输出，发送传输结束标志
                        socketChannel.shutdownOutput();
                    } finally {
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        // 服务端
        // 1.获取ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 2.设置非阻塞通道
        serverSocketChannel.configureBlocking(false);
        // 3.监听连接
        serverSocketChannel.bind(new InetSocketAddress(PORT));
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    SocketChannel accept = serverSocketChannel.accept();
                    // 因为是非阻塞的，所以accept监听客户连接的方法是直接返回的，所以需要判断返回值是否为空，不为空那么客户端就建立连接了
                    // 自旋监听
                    while (accept == null) {
                        accept = serverSocketChannel.accept();
                    }
                    log.info("===>客户连接");
                    // 获取数据流
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    // 阻塞读
                    accept.read(buffer);
                    // 切换读模式，获取数据
                    buffer.flip();
                    byte[] bytes = new byte[buffer.limit()];
                    for (int i = 0; i < buffer.limit(); i++) {
                        bytes[i] = buffer.get();
                    }
                    String data = new String(bytes);
                    log.info("===>获取的数据: {}", data);
                    closable = true;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        long startTime = System.currentTimeMillis();
        // 自旋查询连接是否已完成
        while (!closable) {
        }
        long endTime = System.currentTimeMillis();
        // 关闭通道
        socketChannel.close();
        serverSocketChannel.close();
        executor.shutdown();
        log.info("===>传输时间：{}ms", (endTime - startTime));
    }
}
