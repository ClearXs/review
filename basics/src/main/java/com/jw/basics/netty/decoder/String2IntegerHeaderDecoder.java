package com.jw.basics.netty.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
public class String2IntegerHeaderDecoder {

    static class StringToIntegerDecoder extends ByteToMessageDecoder {

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            // 可读字节小于4，数据包还没有读满
            if (in.readableBytes() < 4) {
                return;
            }
            // 设置回滚点
            in.markReaderIndex();
            // 获取能读字节长度
            int length = in.readInt();
            // 可读字节小于读取出来的字节，说明剩余长度不够消息体（比如说：7个字节 读出来4个字节，那么此时还剩3个字节可读，但下一个字节是4个字节）
            if (in.readableBytes() < length) {
                // 重置读指针
                in.resetReaderIndex();
                return;
            }
            // 读取数据
            byte[] bytes = new byte[length];
            in.readBytes(bytes, 0, length);
            out.add(new String(bytes));
        }

    }

    static class StringLogHandler extends SimpleChannelInboundHandler<String> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            log.info("读取字节，{}", msg);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ChannelInitializer<EmbeddedChannel> initializer = new ChannelInitializer<EmbeddedChannel>() {

            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(new StringToIntegerDecoder());
            }
        };
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(initializer);
        for (int i = 0; i < 3000; i++) {
            embeddedChannel.write("adjl" + i);
        }
        embeddedChannel.flush();

        // 取得出站数据包
        String o = embeddedChannel.readOutbound();
        while (o != null) {
            log.info("出站数据：{}", o);
            o = embeddedChannel.readOutbound();
        }
        TimeUnit.SECONDS.sleep(100);
    }

}
