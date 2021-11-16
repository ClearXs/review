package com.jw.basics.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Byte2IntegerDecoder {

    static class IntegerDecoder extends ReplayingDecoder<Integer> {

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            // 读出三个整数
            out.add(in.readInt());
        }
    }

    static class IntegerHandler extends SimpleChannelInboundHandler<Integer> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Integer msg) throws Exception {
            log.info("读入: {}", msg);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ChannelInitializer<EmbeddedChannel> initializer = new ChannelInitializer<EmbeddedChannel>() {

            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(new IntegerDecoder(), new IntegerHandler());
            }
        };
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(initializer);
        for (int i = 0; i < 100; i++) {
            ByteBuf buffer = Unpooled.buffer(1);
            buffer.writeInt(i);
            embeddedChannel.writeInbound(buffer);
        }
        TimeUnit.SECONDS.sleep(100);
    }
}
