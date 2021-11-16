package com.jw.basics.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.embedded.EmbeddedChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

@Slf4j
public class OutHandlerLifeCircle extends ChannelOutboundHandlerAdapter {

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("被调用：handlerAdded()");
        super.handlerAdded(ctx);
    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("被调用： handlerRemoved()");
        super.handlerRemoved(ctx);
    }

    @Override
    public void bind(ChannelHandlerContext ctx, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        log.info("被调用： bind()");
        super.bind(ctx, localAddress, promise);
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) throws Exception {
        log.info("被调用： connect()");
        super.connect(ctx, remoteAddress, localAddress, promise);
    }

    @Override
    public void disconnect(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        log.info("被调用： disconnect()");
        super.disconnect(ctx, promise);
    }

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        log.info("被调用： read()");
        super.read(ctx);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        log.info("被调用： write()");
        super.write(ctx, msg, promise);
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        log.info("被调用： flush()");
        super.flush(ctx);
    }

    public static void main(String[] args) throws InterruptedException {
        ChannelInitializer<EmbeddedChannel> initializer = new ChannelInitializer<EmbeddedChannel>() {

            @Override
            protected void initChannel(EmbeddedChannel ch) throws Exception {
                ch.pipeline().addLast(new OutHandlerLifeCircle());
            }
        };
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(initializer);
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeInt(1);
        ChannelFuture channelFuture = embeddedChannel.pipeline().writeAndFlush(buffer);
        buffer.writeInt(1);
        channelFuture = embeddedChannel.pipeline().writeAndFlush(buffer);
        channelFuture.addListener((future -> {
            if (future.isSuccess()) {
                System.out.println("write is finished");
            }
            embeddedChannel.close();
        }));
        TimeUnit.SECONDS.sleep(1000);
    }
}
