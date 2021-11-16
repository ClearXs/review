//package com.jw.basics.netty.protocol;
//
//import io.netty.bootstrap.ServerBootstrap;
//import io.netty.buffer.PooledByteBufAllocator;
//import io.netty.channel.*;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.channel.socket.nio.NioServerSocketChannel;
//import io.netty.handler.codec.protobuf.ProtobufDecoder;
//import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class ProtobufServer {
//
//    static class ProtobufBizDecoder extends SimpleChannelInboundHandler<MsgProtos.Msg> {
//
//        @Override
//        protected void channelRead0(ChannelHandlerContext ctx, MsgProtos.Msg msg) throws Exception {
//            log.info("数据包：{}", msg);
//        }
//    }
//
//    private ServerBootstrap serverBootstrap = new ServerBootstrap();
//
//    public void run() {
//        NioEventLoopGroup boss = new NioEventLoopGroup();
//        NioEventLoopGroup worker = new NioEventLoopGroup();
//        try {
//            serverBootstrap.group(boss, worker);
//            serverBootstrap.channel(NioServerSocketChannel.class);
//
//            serverBootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
//            serverBootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
//            serverBootstrap.option(ChannelOption.SO_KEEPALIVE, true);
//
//            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
//                @Override
//                protected void initChannel(SocketChannel ch) throws Exception {
//                    ch.pipeline().addLast(
//                            new ProtobufVarint32FrameDecoder(),
//                            new ProtobufDecoder(MsgProtos.Msg.getDefaultInstance()),
//                            new ProtobufBizDecoder()
//                    );
//                }
//            });
//
//            ChannelFuture channelFuture = serverBootstrap.bind(8080).sync();
//            Channel channel = channelFuture.channel();
//            ChannelFuture closeFuture = channel.closeFuture();
//            closeFuture.sync();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            worker.shutdownGracefully();
//            boss.shutdownGracefully();
//        }
//    }
//
//    public static void main(String[] args) {
//        ProtobufServer server = new ProtobufServer();
//        server.run();
//    }
//}
