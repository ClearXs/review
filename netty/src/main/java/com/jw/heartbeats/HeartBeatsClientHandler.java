package com.jw.heartbeats;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeartBeatsClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 重连重试次数
     */
    private final int tryTimes = 3;

    private volatile int connectCount = 0;

    /**
     * 通道激活回调事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端通道激活");
        super.channelActive(ctx);
    }

    /**
     * 通道不活跃回调事件
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端通道关闭");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        // 写超时事件
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            // 写超时，或者说4秒没有写
            if (idleStateEvent.state() == IdleState.WRITER_IDLE) {
                // 重试，发送心跳包
                if (connectCount <= tryTimes) {
                    log.info("客户端重连...{}", connectCount);
                    connectCount++;
                    ctx.channel().writeAndFlush(123).addListener(new ChannelFutureListener() {
                        @Override
                        public void operationComplete(ChannelFuture future) throws Exception {
                            System.out.println(future);
                        }
                    });
                }
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
