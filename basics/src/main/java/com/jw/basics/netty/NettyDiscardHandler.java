package com.jw.basics.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyDiscardHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        log.info("收到消息：");
        try {
            while (byteBuf.isReadable()) {
                log.info(String.valueOf(byteBuf.readChar()));
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
