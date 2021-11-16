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
public class Byte2IntegerAddDecoder {

    /**
     * 使用ReplayingDecoder的state属性，记录当前读取ByteBuf的位置（读断点指针）
     * 这个解码器的功能是：解析两个整数，使之相加，并传递到后面额处理器中
     */
    static class IntegerAddDecoder extends ReplayingDecoder<IntegerAddDecoder.Status> {

        private int first;

        private int second;

        IntegerAddDecoder() {
            super(Status.P_1);
        }

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            switch (state()) {
                case P_1:
                    first = in.readInt();
                    // 设置读断点指针为当前读取位置，并设置state为p2
                    checkpoint(Status.P_2);
                    break;
                case P_2:
                    second = in.readInt();
                    out.add(first + second);
                    // 设置读断点指针为当前读取位置，并设置state为p1
                    checkpoint(Status.P_1);
                    break;
                default:
                    break;
            }
        }

        enum Status { P_1, P_2 }
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
                ch.pipeline().addLast(new IntegerAddDecoder(), new IntegerHandler());
            }
        };
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(initializer);
        for (int i = 0; i < 4; i++) {
            ByteBuf buffer = Unpooled.buffer(1);
            buffer.writeInt(i);
            embeddedChannel.writeInbound(buffer);
        }
        TimeUnit.SECONDS.sleep(100);
    }
}
