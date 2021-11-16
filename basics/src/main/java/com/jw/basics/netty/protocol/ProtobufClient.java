//package com.jw.basics.netty.protocol;
//
//import io.netty.bootstrap.Bootstrap;
//import io.netty.buffer.PooledByteBufAllocator;
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelFuture;
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.ChannelOption;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.channel.socket.nio.NioSocketChannel;
//import io.netty.handler.codec.protobuf.ProtobufEncoder;
//import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class ProtobufClient {
//
//
//    private Bootstrap bootstrap = new Bootstrap();
//
//    public void run() {
//        NioEventLoopGroup worker = new NioEventLoopGroup();
//        try {
//            bootstrap.group(worker);
//
//            bootstrap.channel(NioSocketChannel.class);
//
//            bootstrap.remoteAddress("localhost", 8080);
//
//            bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
//
//            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
//                @Override
//                protected void initChannel(SocketChannel ch) throws Exception {
//                    // 注意顺序，先经过ProtobufEncoder把Protobuf编码成字节数组，才经过ProtobufVarint32LengthFieldPrepender加上序列化字节数
//                    ch.pipeline().addLast(
//                            new ProtobufVarint32LengthFieldPrepender(),
//                            new ProtobufEncoder()
//                    );
//                }
//            });
//            ChannelFuture channelFuture = bootstrap.connect();
//            channelFuture.addListener(future -> {
//                if (future.isSuccess()) {
//                    log.info("连接成功");
//                } else {
//                    log.info("连接失败");
//                }
//            });
//            channelFuture.sync();
//            Channel channel = channelFuture.channel();
//            for (int i = 0; i < 10000; i++) {
//                // 发送protobuf对象
//                channel.writeAndFlush(build(i, "测试protobuf"));
//            }
//            channel.flush();
//            ChannelFuture closeFuture = channel.closeFuture();
//            closeFuture.sync();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            worker.shutdownGracefully();
//        }
//    }
//
//    private MsgProtos.Msg build(int id, String message) {
//        MsgProtos.Msg.Builder builder = MsgProtos.Msg.newBuilder();
//        builder.setId(id);
//        builder.setContent(message);
//        return builder.build();
//    }
//
//    public static void main(String[] args) {
//        ProtobufClient protobufClient = new ProtobufClient();
//        protobufClient.run();
//    }
//}
