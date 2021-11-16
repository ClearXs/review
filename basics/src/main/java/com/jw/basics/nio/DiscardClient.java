package com.jw.basics.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

@Slf4j
public class DiscardClient {

    public static void startClient() throws IOException {
        // 1.获取通道并绑定连接
        SocketChannel channel = SocketChannel.open(new InetSocketAddress("localhost", 8080));
        // 2.设置成非阻塞模式
        channel.configureBlocking(false);
        // 3.自旋等待与服务器连接
        while (!channel.finishConnect()) {
            log.info("===>等待与服务器连接");
        }
        log.info("===>服务器连接成功");
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("test".getBytes());
        buffer.flip();
        // 4.发送数据到服务器
        channel.write(buffer);
        channel.shutdownOutput();
        channel.close();
    }

    public static void main(String[] args) throws IOException {
        startClient();
    }
}
