package com.jw.basics.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

public class SimpleSelector {

    public static void main(String[] args) throws IOException {
        // 1.获取Selector实例 (SPI方式)
        Selector selector = Selector.open();
        // 2.获取通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 3.设置非阻塞模式
        serverSocketChannel.configureBlocking(false);
        // 4.绑定连接
        serverSocketChannel.bind(new InetSocketAddress(8080));
        // 获取该通道支持的选择器事件
        int i = serverSocketChannel.validOps();
        // 5.通道注册到选择器上，并监听接收连接事件。注册到选择器的通道必须是非阻塞模式，否则抛出IllegalBlockingModeException
        // 值得说明的几点是，并不是所有的通道都支持所有的IO事件
        // - ServerSocketChannel仅仅支持OP_ACCEPT
        // - SocketChannel不支持OP_ACCEPT
        // - FileChannel不能注册到选择器上，因为它仅支持阻塞模式
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        // select方法选出已经注册、就绪的IO事件。返回Selector感兴趣IO事件通道的数量
        while (selector.select() > 0) {
            // 这个Set集合是那些注册、就绪好的IO事件
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isReadable()) {
                    // IO通道可读事件
                }
                if (selectionKey.isAcceptable()) {
                    // IO服务器通道接收事件
                }
                if (selectionKey.isConnectable()) {
                    // IO通道连接成功事件
                }
                if (selectionKey.isWritable()) {
                    // IO通道可写事件
                }
                // 当处理完相应事件后移除当前IO事件，避免下次循环继续调用
                iterator.remove();
            }
        }
    }
}
