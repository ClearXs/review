package com.jw.basics.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class NettyEchoClient {

    private final Bootstrap bootstrap = new Bootstrap();


    public void run() {
        // 创建反应器组
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            // 1.设置反应器
            bootstrap.group(worker);
            // 2.设置通道类型
            bootstrap.channel(NioSocketChannel.class);
            // 3.设置监听通道
            bootstrap.remoteAddress("localhost", 8080);
            // 4.设置通道参数
            bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            // 5.装配流水线
            bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new EchoHandler());
                }
            });
            // 6.连接
            ChannelFuture connect = bootstrap.connect();
            connect.addListener(future -> {
               if (future.isSuccess()) {
                   log.info("连接成功");
               } else {
                   log.info("连接失败");
               }
            });
            // 阻塞直至完成
            connect.sync();
            Channel channel = connect.channel();
            // 7.发送内容，从池化缓冲区获取一个缓冲区
            ByteBuf buffer = channel.alloc().buffer();
            // 写入发送的字节
            buffer.writeBytes("测试".getBytes());
            // 写入管道中
            ChannelFuture future = channel.writeAndFlush(buffer);
            // 阻塞直到发送成功
            future.sync();
            ChannelFuture closeFuture = channel.closeFuture();
            closeFuture.sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
        }
    }

    @ChannelHandler.Sharable
    public static class EchoHandler extends SimpleChannelInboundHandler<ByteBuf> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
            int length = msg.readableBytes();
            byte[] bytes = new byte[length];
            for (int i = 0; i < length; i++) {
                bytes[i] = msg.getByte(i);
            }
            log.info("接收的内容：{}", new String(bytes));
//            ctx.channel().close();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        NettyEchoClient client = new NettyEchoClient();
        client.run();
        TimeUnit.SECONDS.sleep(2);
        Thread.currentThread().interrupt();
    }
}
