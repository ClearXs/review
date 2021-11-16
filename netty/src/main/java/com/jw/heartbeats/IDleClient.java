package com.jw.heartbeats;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class IDleClient {

    private NioEventLoopGroup worker;

    private Channel channel;

    public void start() throws InterruptedException {
        try {
            connect("localhost", 8080);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
            // 重连
            connect("localhost", 8080);
        }
    }

    public void connect(String host, int port) throws InterruptedException {
        worker = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(worker);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(
                        new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS),
                        new HeartBeatsClientHandler()
                );
            }
        });
        ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
        channel = channelFuture.channel();
        channel.closeFuture().sync();
    }

    public void closed() {
        if (channel != null) {
            channel.close();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        IDleClient iDleClient = new IDleClient();
        iDleClient.start();

        TimeUnit.SECONDS.sleep(10);
        iDleClient.closed();
    }
}
