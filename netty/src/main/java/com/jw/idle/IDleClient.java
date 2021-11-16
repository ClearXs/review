package com.jw.idle;

import com.jw.heartbeats.HeartBeatsClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.HashedWheelTimer;

import java.util.concurrent.TimeUnit;

public class IDleClient {

    private NioEventLoopGroup worker;

    private Channel channel;

    protected final HashedWheelTimer timer = new HashedWheelTimer();

    public void start(String host, int port) {
        try {
            worker = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(worker);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.TCP_NODELAY, true);
            ConnectionWatchDog connectionWatchDog = new ConnectionWatchDog(bootstrap, timer, host, port, true) {

                @Override
                public ChannelHandler[] channelHandlers() {
                    return new ChannelHandler[] {
                            new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS),
                            this,
                            new com.jw.idle.HeartBeatsClientHandler()
                    };
                }
            };
            ChannelFuture future;
            synchronized (bootstrap) {
                bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(connectionWatchDog);
                    }
                });
                future = bootstrap.connect(host, port);
            }
            future.sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        IDleClient iDleClient = new IDleClient();
        iDleClient.start("localhost", 8080);
    }
}
