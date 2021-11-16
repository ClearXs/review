package com.jw.basics.netty.encoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.MessageToMessageEncoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 将Java POJO编码成另外一个Java POJO对象
 * @author jiangw
 * @date 2020/11/21 21:29
 * @since 1.0
 */
@Slf4j
public class String2IntegerEncoder {

    /**
     * 抽取字符串的数字，传输到下一站
     */
    static class StringToIntegerEncoder extends MessageToMessageEncoder<String> {

        @Override
        protected void encode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
            char[] chars = msg.toCharArray();
            for (char aChar : chars) {
                // 0-9的数字
                if (aChar > 47 && aChar < 58) {
                    out.add(new Integer(aChar));
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ChannelInitializer<EmbeddedChannel> initializer = new ChannelInitializer<EmbeddedChannel>() {

            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(new StringToIntegerEncoder());
            }
        };
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(initializer);
        for (int i = 0; i < 100; i++) {
            embeddedChannel.write("adjl" + i);
        }
        embeddedChannel.flush();

        // 取得出站数据包
        Integer o = embeddedChannel.readOutbound();
        while (o != null) {
            log.info("出站数据：{}", o);
            o = embeddedChannel.readOutbound();
        }
        TimeUnit.SECONDS.sleep(100);
    }
}
