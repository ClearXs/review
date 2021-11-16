package com.jw.basics.nio.reactor;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

@Slf4j
public class EchoClient {

    public void start() throws IOException {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 8080));
        socketChannel.configureBlocking(false);
        // 等待服务器响应
        while (!socketChannel.finishConnect()) {
        }
        Processor processor = new Processor(socketChannel);
        new Thread(processor).start();
    }

    static class Processor implements Runnable {

        final Selector selector;

        final SocketChannel socketChannel;

        Processor(SocketChannel socketChannel) throws IOException {
            this.selector = Selector.open();
            this.socketChannel = socketChannel;
            // 通道注册事件
            socketChannel.register(this.selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ);
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    selector.select();
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        // IO读事件
                        if (selectionKey.isWritable()) {
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            String message = "test";
                            SocketChannel channel = (SocketChannel) selectionKey.channel();
                            buffer.put(message.getBytes());
                            // 转换成读模式
                            buffer.flip();
                            channel.write(buffer);
                            buffer.clear();
                        }
                        if (selectionKey.isReadable()) {
                            SocketChannel channel = (SocketChannel) selectionKey.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            int length;
                            while ((length = channel.read(buffer)) > 0) {
                                buffer.flip();
                                log.info("客户端接收数据：{}", new String(buffer.array()));
                            }
                        }
                    }
                    selectionKeys.clear();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        EchoClient echoClient = new EchoClient();
        echoClient.start();
    }
}
