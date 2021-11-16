package com.jw.basics.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyEchoServer {

    private final ServerBootstrap serverBootstrap = new ServerBootstrap();

    public void run() {
        // 1.创建反应器
        // 创建请求连接的反应器
        NioEventLoopGroup boss = new NioEventLoopGroup();
        // 创建处理业务的反应器
        NioEventLoopGroup worker = new NioEventLoopGroup();

        try {
            // 2.绑定反应器
            serverBootstrap.group(worker, worker);
            // 3.设置NIO类型
            serverBootstrap.channel(NioServerSocketChannel.class);
            // 4.设置通道参数
            // 长连接
            serverBootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            // 池化缓冲区
            serverBootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            serverBootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            // 5.装配流水线
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new EchoHandler());
                }
            });
            // 6.客户端绑定server
            ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();
            log.info("服务器启动");

            // 7.等待通道关闭的异步任务
            ChannelFuture closeFuture = channelFuture.channel().closeFuture();
            closeFuture.sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放反应器资源，包括线程
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }

    @ChannelHandler.Sharable
    static class EchoHandler extends SimpleChannelInboundHandler<ByteBuf> {

        @Override
        public void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {
            int length = msg.readableBytes();
            byte[] bytes = new byte[length];
            for (int i = 0; i < length; i++) {
                bytes[i] = msg.getByte(i);
            }
            ByteBuf write = ctx.channel().alloc().buffer();
            write.writeBytes("会写".getBytes());
            log.info("服务接收消息：{}", new String(bytes));
            ChannelFuture channelFuture = ctx.writeAndFlush(write);
            log.info("写回前，msg.refCnt:" + msg.refCnt());
            // 监听写回的消息
            channelFuture.addListener(future -> {
                log.info("写回后，msg.refCnt:" + msg.refCnt());
            });
        }
    }

    public static void main(String[] args) {
        NettyEchoServer server = new NettyEchoServer();
        server.run();
    }
}
