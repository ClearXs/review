package com.jw.basics.netty.rpc.client;

import com.jw.basics.netty.rpc.Demo;
import com.jw.basics.netty.rpc.RemoteObject;
import com.jw.basics.netty.rpc.RpcRequest;
import com.jw.basics.netty.rpc.RpcResponse;
import com.jw.basics.netty.rpc.api.DemoService;
import com.jw.basics.netty.rpc.codec.ByteDecoder;
import com.jw.basics.netty.rpc.codec.ByteEncoder;
import com.jw.basics.netty.rpc.handler.RpcResponseHandler;
import com.jw.basics.netty.rpc.serialization.Serialize;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class NettyClient implements Client {

    private Bootstrap bootstrap = new Bootstrap();

    private NioEventLoopGroup worker;

    private final static String HOST = "localhost";

    private final static int PORT = 8080;

    private final Serialize serialize;

    private Channel channel;

    public NettyClient(Serialize serialize) {
        this.serialize = serialize;
    }

    @Override
    public void start() {
        worker = new NioEventLoopGroup();
        try {
            bootstrap.group(worker);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
            bootstrap.remoteAddress(HOST, PORT);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    // 编码器->出站
                    // 解码器->入站
                    // 处理
                    ch.pipeline().addLast(
                            new LengthFieldPrepender(4),
                            new ByteEncoder(RpcRequest.class, serialize),
                            new ByteDecoder(RpcResponse.class, serialize),
                            new RpcResponseHandler());
                }
            });
            ChannelFuture future = bootstrap.connect();
            future.addListener(future1 -> {
                if (future.isSuccess()) {
                    log.info("连接成功");
                } else {
                    log.info("连接失败");
                }
            });
            future.sync();
            this.channel = future.channel();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onStop() throws InterruptedException {
        if (this.channel.isActive()) {
            this.channel.close().sync();
        }
        if (worker != null) {
            worker.shutdownGracefully();
        }
        log.info("client... shutdown");
        return true;
    }

    @Override
    public ChannelFuture send(RpcRequest rpcRequest) {
        return this.channel.writeAndFlush(rpcRequest);
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            TimeUnit.MILLISECONDS.sleep(400);
            int finalI = i;
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    RpcRequest rpcRequest = new RpcRequest();
                    rpcRequest.setInterfaceClass(DemoService.class);
                    rpcRequest.setParameters(null);
                    rpcRequest.setParameterTypes(null);
                    rpcRequest.setRequestId("123" + finalI);
                    rpcRequest.setServiceName(DemoService.class.getName());
                    RemoteObject remoteObject = new RemoteObject(rpcRequest);
                    DemoService demoService = (DemoService) remoteObject.getObject();
                    Demo demo = demoService.outDemo(rpcRequest.getRequestId());
                    System.out.println(demo);
                }
            });
        }

    }
}
