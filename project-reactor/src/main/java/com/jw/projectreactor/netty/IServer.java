package com.jw.projectreactor.netty;

import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;

public class IServer {

    public static void main(String[] args) {

        DisposableServer server = TcpServer
                .create() // 创建一个TcpServer实例
                .bindNow(); // 以阻塞的方式启动server，并等待它完成

        server.onDispose()
                .block();
    }
}
