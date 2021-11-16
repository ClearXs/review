package com.jw.basics.netty.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class Byte2IntegerEncoder {

    static class IntegerEncoder extends MessageToByteEncoder<Integer> {

        @Override
        protected void encode(ChannelHandlerContext ctx, Integer msg, ByteBuf out) throws Exception {
            // 数据包小于4个字节丢弃。
            if (out.readableBytes() < 4) {
                return;
            }
            out.writeInt(msg);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ChannelInitializer<EmbeddedChannel> initializer = new ChannelInitializer<EmbeddedChannel>() {

            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(new IntegerEncoder());
            }
        };
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(initializer);
        // 向管道写入数据
        for (int i = 0; i < 100; i++) {
            embeddedChannel.write(i);
        }
        embeddedChannel.flush();
        // 取得出站数据包
        ByteBuf o = embeddedChannel.readOutbound();
        while (o != null) {
            log.info("出站数据：{}", o.readInt());
            o = embeddedChannel.readOutbound();
        }
        TimeUnit.SECONDS.sleep(100);
    }
}
