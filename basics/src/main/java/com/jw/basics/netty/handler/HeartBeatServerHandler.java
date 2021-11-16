package com.jw.basics.netty.handler;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * 服务器端进行空闲检测，每隔一段时间内如果没有数据入站，那么就判断连接假死
 * @author jiangw
 * @date 2020/11/22 16:16
 * @since 1.0
 */
public class HeartBeatServerHandler extends IdleStateHandler {

    public HeartBeatServerHandler(int readerIdleTimeSeconds, int writerIdleTimeSeconds, int allIdleTimeSeconds) {
        super(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds);
    }

    public HeartBeatServerHandler(long readerIdleTime, long writerIdleTime, long allIdleTime, TimeUnit unit) {
        super(readerIdleTime, writerIdleTime, allIdleTime, unit);
    }

    public HeartBeatServerHandler(boolean observeOutput, long readerIdleTime, long writerIdleTime, long allIdleTime, TimeUnit unit) {
        super(observeOutput, readerIdleTime, writerIdleTime, allIdleTime, unit);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        // 判断假死后回调方法，用于关闭连接
        super.channelIdle(ctx, evt);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // 判断消息是不是心跳报文
        if (msg == null) {
            super.channelRead(ctx, msg);
        }
        // 收到客户端的心跳报文，向客户端回写心跳数据包
        ChannelFuture channelFuture = ctx.channel().writeAndFlush(msg);
    }
}
