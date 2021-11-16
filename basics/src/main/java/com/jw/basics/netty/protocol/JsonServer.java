package com.jw.basics.netty.protocol;

import com.jw.basics.netty.Entity;
import com.jw.basics.netty.JsonUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonServer {

    private ServerBootstrap serverBootstrap = new ServerBootstrap();

    static class JsonMsgDecoder extends SimpleChannelInboundHandler<String> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            // 解析json字符串为实体
            Entity entity = JsonUtil.jsonToPojo(msg, Entity.class);
            log.info("实体：{}", entity);
            super.channelRead(ctx, entity);
        }
    }

    static class BizHandler extends SimpleChannelInboundHandler<Entity> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Entity msg) throws Exception {
            log.info("处理业务, {}", msg);
        }
    }

    public void run() {
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            serverBootstrap.group(boss, worker);
            serverBootstrap.channel(NioServerSocketChannel.class);

            serverBootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            serverBootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            serverBootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

            // 装配流水线
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(
                            new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4),
                            new StringDecoder(),
                            new JsonMsgDecoder(),
                            new BizHandler());
                }
            });

            ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();

            ChannelFuture closeFuture = channelFuture.channel().closeFuture();
            closeFuture.sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        JsonServer jsonServer = new JsonServer();
        jsonServer.run();
    }
}
