package com.jw.basics.netty.rpc;

import java.util.concurrent.ExecutionException;

/**
 * 异步任务的响应获取
 * @author jiangw
 * @date 2020/11/23 15:53
 * @since 1.0
 */
public class RpcFutureResponse {

    private RpcRequest rpcRequest;

    public RpcFutureResponse(RpcRequest rpcRequest) {
        this.rpcRequest = rpcRequest;
    }

    public RpcResponse get() throws ExecutionException, InterruptedException {
        RpcResponse response = RpcRequestPool.add(rpcRequest);
        return response;
    }
}
