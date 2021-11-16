package com.jw.basics.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyDiscardServer {

    private final int port = 8080;

    // 创建一个服务器端的启动器
    private final ServerBootstrap serverBootstrap = new ServerBootstrap();

    public void run() {
        // 创建反应器线程组-与多线程反应器一致
        NioEventLoopGroup boseGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup(2);
        try {
            // 1.设置反应器组
            serverBootstrap.group(boseGroup, workGroup);
            // 2.设置NIO通道类型
            serverBootstrap.channel(NioServerSocketChannel.class);
            // 3.设置监听端口
            serverBootstrap.localAddress(port);
            // 4.通道参数
            serverBootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            serverBootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            // 5.装配子通道流水线
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                // 当有链接时创建一个channel
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    // 向子channel流水线添加一个handler
                    ch.pipeline().addLast(new NettyDiscardHandler());
                }
            });
            // 6.绑定server，通过sync同步至绑定成功
            ChannelFuture channelFuture = serverBootstrap.bind().sync();
            log.info("服务器启动成功，监听端口：{}", channelFuture.channel().localAddress());
            // 7.等待通道关闭的异步任务结束，服务器监听通道会一直等待通道关闭的异步任务结束
            ChannelFuture closeFuture = channelFuture.channel().closeFuture();
            closeFuture.sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 8.关闭
            boseGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        NettyDiscardServer nettyDiscardServer = new NettyDiscardServer();
        nettyDiscardServer.run();
    }
}
