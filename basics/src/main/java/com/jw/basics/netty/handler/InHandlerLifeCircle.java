package com.jw.basics.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * 入站处理器的生命周期
 * @author jiangw
 * @date 2020/11/20 21:41
 * @since 1.0
 */
@Slf4j
public class InHandlerLifeCircle extends ChannelInboundHandlerAdapter {

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("被调用：handlerAdded()");
        super.handlerAdded(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        log.info("被调用：channelRegistered()");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("被调用：channelActive()");
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("被调用：channelRead()");
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        log.info("被调用：channelReadComplete()");
        super.channelReadComplete(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("被调用：channelInactive()");
        super.channelInactive(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        log.info("被调用: channelUnregistered()");
        super.channelUnregistered(ctx);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("被调用：handlerRemoved()");
        super.handlerRemoved(ctx);
    }

    public static void main(String[] args) throws InterruptedException {
        // 初始化处理器
        ChannelInitializer<EmbeddedChannel> initializer = new ChannelInitializer<EmbeddedChannel>() {

            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                // 添加处理器
                ch.pipeline().addLast(new InHandlerLifeCircle());
            }
        };

        // 创建嵌入式通道
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(initializer);
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(1);
        // 模拟入站，写入一个入站数据包
        embeddedChannel.writeInbound(buffer);
        buffer.writeInt(2);
        // 模拟入站，写入一个入站数据包
        embeddedChannel.writeInbound(buffer);
        buffer.writeInt(3);
        embeddedChannel.writeInbound(buffer);
        embeddedChannel.finish();
        embeddedChannel.close();
        TimeUnit.SECONDS.sleep(2000);
    }
}
