package com.jw.basics.netty.protocol;

import com.jw.basics.netty.NettyEchoClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * 半包问题示例
 * @author jiangw
 * @date 2020/11/21 22:00
 * @since 1.0
 */
@Slf4j
public class NettyDumpSendClient {

    private Bootstrap bootstrap = new Bootstrap();

    public void run() {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            // 设置反应器
            bootstrap.group(worker);
            // 设置通道类型
            bootstrap.channel(NioSocketChannel.class);
            // 设置监听端口
            bootstrap.remoteAddress("localhost", 8080);
            // 设置通道参数
            bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            // 装配handler
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new NettyEchoClient.EchoHandler());
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
            // 阻塞直到连接完成
            connect.sync();
            Channel channel = connect.channel();
            // 发送大量的文字
            byte[] bytes = "测试半包问题。。。".getBytes(Charset.forName("utf-8"));
            for (int i = 0; i < 1000; i++) {
                //发送ByteBuf
                ByteBuf buffer = channel.alloc().buffer();
                buffer.writeBytes(bytes);
                channel.writeAndFlush(buffer);
            }
            // 阻塞等待关闭
            ChannelFuture channelFuture = channel.closeFuture();
            channelFuture.sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        NettyDumpSendClient nettyDumpSendClient = new NettyDumpSendClient();
        nettyDumpSendClient.run();
    }
}
