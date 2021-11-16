package com.jw.basics.netty.rpc.server;

import com.jw.basics.netty.rpc.RpcRequest;
import com.jw.basics.netty.rpc.RpcResponse;
import com.jw.basics.netty.rpc.ServiceProvider;
import com.jw.basics.netty.rpc.api.DemoService;
import com.jw.basics.netty.rpc.api.DemoServiceImpl;
import com.jw.basics.netty.rpc.codec.ByteDecoder;
import com.jw.basics.netty.rpc.codec.ByteEncoder;
import com.jw.basics.netty.rpc.handler.RpcRequestHandler;
import com.jw.basics.netty.rpc.serialization.ProtostuffSer;
import com.jw.basics.netty.rpc.serialization.Serialize;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class NettyServer implements Server {

    private final static int PORT = 8080;

    private ServerBootstrap serverBootstrap = new ServerBootstrap();

    private NioEventLoopGroup boss;
    private NioEventLoopGroup worker;

    private final Serialize serialize;


    public NettyServer(Serialize serialize) {
        this.serialize = serialize;
    }

    @Override
    public void start() {
        boss = new NioEventLoopGroup();
        worker = new NioEventLoopGroup();
        try {
            serverBootstrap.group(boss, worker);

            serverBootstrap.channel(NioServerSocketChannel.class);

            serverBootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            serverBootstrap.childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            serverBootstrap.option(ChannelOption.SO_KEEPALIVE, true);

            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    // 编码->出站
                    // 节码->入站
                    // 请求处理器->入站
                    ch.pipeline().addLast(
                            new LengthFieldBasedFrameDecoder(1024, 0, 4, 0, 4),
                            new ByteEncoder(RpcResponse.class, serialize),
                            new ByteDecoder(RpcRequest.class, serialize),
                            new RpcRequestHandler());
                }
            });
            ChannelFuture channelFuture = serverBootstrap.bind(PORT).sync();
            Channel channel = channelFuture.channel();
            ChannelFuture closeFuture = channel.closeFuture();
            closeFuture.sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    @Override
    public boolean onStop() {
        if (boss != null) {
            boss.shutdownGracefully();
        }
        if (worker != null) {
            worker.shutdownGracefully();
        }
        return true;
    }

    public static void main(String[] args) {
        ServiceProvider.getInstance().addService(DemoService.class.getName(), DemoServiceImpl.class);
        NettyServer nettyServer = new NettyServer(new ProtostuffSer());
        nettyServer.start();
    }
}
