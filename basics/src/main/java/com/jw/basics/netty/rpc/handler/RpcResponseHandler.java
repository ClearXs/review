package com.jw.basics.netty.rpc.handler;

import com.jw.basics.netty.rpc.RpcRequestPool;
import com.jw.basics.netty.rpc.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcResponseHandler extends SimpleChannelInboundHandler<RpcResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcResponse rpcResponse) throws Exception {
        log.info(rpcResponse.toString());
        RpcRequestPool.notify(rpcResponse);
    }
}
