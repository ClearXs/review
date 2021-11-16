package com.jw.idle;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 客户端重连检测
 */
@Slf4j
@ChannelHandler.Sharable
public abstract class ConnectionWatchDog extends ChannelInboundHandlerAdapter implements TimerTask, ChannelHandlerHolder {

    private final Bootstrap bootstrap;

    private final Timer timer;

    private final String host;

    private final int port;

    private volatile boolean reconnect = true;

    /**
     * 重连次数，考虑到这个处理器是共享的，所以会出现线程不安全的情况，考虑使用线程本地存储进行操作
     */
    private ThreadLocal<Integer> attempts = new ThreadLocal<>();

    public ConnectionWatchDog(Bootstrap bootstrap, Timer timer, String host, int port, boolean reconnect) {
        this.bootstrap = bootstrap;
        this.timer = timer;
        this.host = host;
        this.port = port;
        this.reconnect = reconnect;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("通道激活，重试次数重置为0: {}", ctx.channel().remoteAddress());
        attempts.set(0);
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("通道关闭");
        if (reconnect) {
            log.info("通道关闭，即将重连");
            // 重连次数
            if (attempts.get() < 12) {
                attempts.set(attempts.get() + 1);
                int timeout = 2 << attempts.get();
                timer.newTimeout(this, timeout, TimeUnit.MILLISECONDS);
            }
        }
        ctx.fireChannelInactive();
    }

    /**
     * 创建当经过指定时间后，执行该方法
     * @param timeout
     * @throws Exception
     */
    @Override
    public void run(Timeout timeout) throws Exception {
        ChannelFuture channelFuture;
        // 添加
        // 复用bootstrap，重新添加处理器并进行重连
        synchronized (bootstrap) {
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(channelHandlers());

                }
            });
            ChannelFuture future = bootstrap.connect(host, port);
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        log.info("重连成功");
                    } else {
                        log.info("重连失败");
                        // 继续进行重连
                        future.channel().pipeline().fireChannelInactive();
                    }
                }
            });
        }
    }
}
