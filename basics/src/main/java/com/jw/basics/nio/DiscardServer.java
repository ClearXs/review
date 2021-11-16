package com.jw.basics.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * 创建Discard服务器
 * @author jiangw
 * @date 2020/11/15 16:02
 * @since 1.0
 */
@Slf4j
public class DiscardServer {

    public static void startServer() throws IOException {
        // 1.获取选择器
        Selector selector = Selector.open();
        // 2.获取通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 3.设置通道为非阻塞
        serverSocketChannel.configureBlocking(false);
        // 4.绑定连接地址
        serverSocketChannel.bind(new InetSocketAddress(8080));
        // 5.注册选择器事件，ServerSocketChannel只能注册OP_ACCEPT事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 6.开始循环选择器中感兴趣的IO事件（此时为OP_ACCEPT），注意select()方法会如果没有事件的话会阻塞
        while (selector.select() > 0) {
            // 7.获取选择器中有的IO事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                // 8.判断键的类型
                if (selectionKey.isAcceptable()) {
                    log.info("===>与客户端开始建立连接");
                    // 9.可连接的IO事件
                    // 此时accept一定有返回值，因为Selector只有连接到来时才会告诉通道，所以即使通道是非阻塞，此时一定有返回值
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    // 10.客户端通道设置非阻塞
                    socketChannel.configureBlocking(false);
                    // 11.将客户端通道注册到选择器上，为读事件
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }
                if (selectionKey.isReadable()) {
                    // 12.同可连接事件一样，可读事件，只有当网络连接有数据时才会读，或者说经历了这样一个过程
                    // 客户端缓冲区写入通道->通道与内核缓冲区做数据交换->内核与网卡数据交换->网卡通过网络连接把数据包传输到远程服务器的网卡上
                    // 服务器网卡与内核缓冲区数据交换->内核缓冲区与通道数据交换->此时通道时客户端的通道所以被selector监听到->selector里面读IO事件生效->告诉服务器程序做处理
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    // 13.读取客户端发送的数据
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int length = 0;
                    while ((length = channel.read(buffer)) > 0) {
                        // 读模式
                        buffer.flip();
                        log.info("===>客户端数据：{}", new String(buffer.array()));
                    }
                    // discard直接关闭客户端通道
                    channel.close();
                }
                // 移除已经处理过的IO事件
                iterator.remove();
            }
        }
        serverSocketChannel.close();
    }

    public static void main(String[] args) throws IOException {
        startServer();
    }
}
