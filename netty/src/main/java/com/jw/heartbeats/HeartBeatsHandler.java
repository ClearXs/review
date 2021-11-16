package com.jw.heartbeats;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeartBeatsHandler extends ChannelInboundHandlerAdapter {

    private volatile int readTimeOutCount = 0;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("读取消息：{}", msg.toString());
        log.info("来自：{}", ctx.channel().remoteAddress());
    }

    /**
     * 超时触发的方法
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("超时响应");
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            // 读超时
            if (idleStateEvent.state() == IdleState.READER_IDLE) {
                readTimeOutCount++;
                log.info("客户端5s未发送心跳包...");
                if (readTimeOutCount > 1) {
                    // 关闭
                    log.info("客户端超过10s未发送心跳包...关闭连接");
                    ctx.channel().close();
                }
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        super.exceptionCaught(ctx, cause);
    }
}
