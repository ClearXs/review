package com.jw.basics.netty.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class InPipeline {

    static class InHandlerA extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            log.info("InHandlerA channelRead()：{}", msg);
            super.channelRead(ctx, msg);
        }
    }

    static class InHandlerB extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            log.info("InHandlerB channelRead()：{}", msg);
            super.channelRead(ctx, msg);
        }
    }

    static class InHandlerC extends ChannelInboundHandlerAdapter {
        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            log.info("InHandlerC channelRead()：{}", msg);
            super.channelRead(ctx, msg);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ChannelInitializer<EmbeddedChannel> initializer = new ChannelInitializer<EmbeddedChannel>() {

            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(new InHandlerA(), new InHandlerB(), new InHandlerC());
            }
        };
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(initializer);
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(1);
        embeddedChannel.writeInbound(buffer);
        embeddedChannel.finish();
        embeddedChannel.close();
        TimeUnit.SECONDS.sleep(2000);
    }
}
