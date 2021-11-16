package com.jw.basics.netty.pipeline;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class OutPipeline {

    static class OutHandlerA extends ChannelOutboundHandlerAdapter {

        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            log.info("OutHandlerA write()：{}", msg);
            super.write(ctx, msg, promise);
        }
    }

    static class OutHandlerB extends ChannelOutboundHandlerAdapter {

        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            log.info("OutHandlerB write()：{}", msg);
            super.write(ctx, msg, promise);
            ctx.fireChannelUnregistered();
        }
    }

    static class OutHandlerC extends ChannelOutboundHandlerAdapter {

        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            log.info("OutHandlerC write()：{}", msg);
            super.write(ctx, msg, promise);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ChannelInitializer<EmbeddedChannel> channelInitializer = new ChannelInitializer<EmbeddedChannel>() {

            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(new OutHandlerA(), new OutHandlerB(), new OutHandlerC());
            }
        };
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(channelInitializer);
        ByteBuf buffer = Unpooled.buffer();
        embeddedChannel.writeOutbound(buffer);
        embeddedChannel.writeOutbound(buffer);
        embeddedChannel.close();
        TimeUnit.SECONDS.sleep(2000);

    }
}
