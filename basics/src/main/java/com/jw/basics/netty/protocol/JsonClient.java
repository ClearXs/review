package com.jw.basics.netty.protocol;

import com.jw.basics.netty.Entity;
import com.jw.basics.netty.JsonUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonClient {

    private Bootstrap bootstrap = new Bootstrap();

    public void run() {
        NioEventLoopGroup worker = new NioEventLoopGroup();
        try {
            bootstrap.group(worker);

            bootstrap.channel(NioSocketChannel.class);

            bootstrap.remoteAddress("localhost", 8080);

            bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

            bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ch.pipeline().addLast(
                            new LengthFieldPrepender(4),
                            new StringEncoder());
                }
            });

            ChannelFuture channelFuture = bootstrap.connect();
            channelFuture.addListener(future -> {
               if (future.isSuccess()) {
                   log.info("连接成功");
               } else {
                   log.info("连接失败");
               }
            });

            channelFuture.sync();

            Channel channel = channelFuture.channel();
            // 发送对象
            for (int i = 0; i < 1000; i++) {
                Entity entity = new Entity();
                entity.setName("测试json序列化: " + i);
                String json = JsonUtil.pojoToJson(entity);
                channel.writeAndFlush(json);
            }
            channel.flush();
            ChannelFuture closeFuture = channel.closeFuture();
            closeFuture.sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        JsonClient jsonClient = new JsonClient();
        jsonClient.run();
    }
}
