package com.jw.basics.netty.rpc.client;

import com.jw.basics.netty.rpc.RpcRequest;
import io.netty.channel.ChannelFuture;

public interface Client {

    void start();

    boolean onStop() throws InterruptedException;

    ChannelFuture send(RpcRequest rpcRequest);
}
