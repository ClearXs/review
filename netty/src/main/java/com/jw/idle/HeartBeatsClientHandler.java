package com.jw.idle;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeartBeatsClientHandler extends ChannelInboundHandlerAdapter {

    private static final ByteBuf HEARTBEATS_MESSAGE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("heart beats message".getBytes()));

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("通道激活");
        super.channelActive(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.WRITER_IDLE) {
                log.info("写超过空闲，开始向服务器写心跳包");
                ctx.channel().writeAndFlush(HEARTBEATS_MESSAGE.duplicate());
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
