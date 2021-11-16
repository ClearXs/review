package com.jw.basics.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class HeartBeatClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 处理服务器端发送回的心跳报文
        if (msg == null) {
            super.channelRead(ctx, msg);
            return;
        }
        log.info(msg.toString());
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);
        // 心跳处理器连接后，定时发送心跳报文
        ctx.executor().schedule(() -> {
            if (ctx.channel().isActive()) {
                ctx.channel().writeAndFlush("心跳报文");
            }
        }, 100, TimeUnit.SECONDS);
    }
}
