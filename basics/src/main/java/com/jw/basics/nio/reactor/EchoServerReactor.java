package com.jw.basics.nio.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 单线程reactor模型的回显服务器，支持把客户端传输的内容传输回客户端
 * @author jiangw
 * @date 2020/11/15 21:03
 * @since 1.0
 */
public class EchoServerReactor implements Runnable {

    private final Selector selector;

    private final ServerSocketChannel serverSocketChannel;

    public EchoServerReactor() throws IOException {
        // 初始化服务器
        this.selector = Selector.open();
        this.serverSocketChannel = ServerSocketChannel.open();
        // 1.通道绑定地址
        serverSocketChannel.socket().bind(new InetSocketAddress("localhost", 8080));
        // 2.设置非阻塞模式
        serverSocketChannel.configureBlocking(false);
        // 3.当前通道注册到选择器上，并获取选择键。
        SelectionKey selectionKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 4.向选择键中添加handler，作为回调对象
        selectionKey.attach(new AcceptHandler());
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                // 阻塞，直到有感兴趣的事件
                this.selector.select();
                Set<SelectionKey> selectionKeys = this.selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
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
        // selectionKey绑定的是OP_ACCEPT,客户端连接事件
        if (handler != null) {
            handler.run();
        }
    }

    class AcceptHandler implements Runnable {

        @Override
        public void run() {
            // 接收请求处理器，创建一个IOHandler
            try {
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    // 连接事件来临
                    new EchoHandler(socketChannel, selector);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Thread(new EchoServerReactor()).start();
    }
}
