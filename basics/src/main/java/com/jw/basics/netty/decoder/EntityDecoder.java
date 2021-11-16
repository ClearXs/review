package com.jw.basics.netty.decoder;

import com.jw.basics.netty.Entity;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class EntityDecoder {

    static class Byte2EntityDecoder extends ByteToMessageDecoder {

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        }
    }

    static class EntityHandler extends SimpleChannelInboundHandler<Entity> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Entity msg) throws Exception {
            log.info("entity: {}", msg);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ChannelInitializer<EmbeddedChannel> initializer = new ChannelInitializer<EmbeddedChannel>() {

            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(new Byte2EntityDecoder(), new EntityHandler());
            }
        };
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(initializer);
        for (int i = 0; i < 100; i++) {
            ByteBuf buffer = Unpooled.buffer();
            Entity entity = new Entity();
            entity.setName("name" + i);
            embeddedChannel.writeInbound(buffer);
        }
        TimeUnit.SECONDS.sleep(100);
    }
}
