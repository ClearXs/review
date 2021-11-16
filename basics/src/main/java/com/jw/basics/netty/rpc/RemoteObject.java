package com.jw.basics.netty.rpc;

import com.jw.basics.netty.rpc.client.Client;
import com.jw.basics.netty.rpc.client.NettyClient;
import com.jw.basics.netty.rpc.serialization.ProtostuffSer;
import io.netty.channel.ChannelFuture;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 远程调用对象
 * @author jiangw
 * @date 2020/11/22 23:54
 * @since 1.0
 */
public class RemoteObject {

    private RpcRequest rpcRequest;

    private Client client;

    public RemoteObject(RpcRequest rpcRequest) {
        this.rpcRequest = rpcRequest;
        this.client = new NettyClient(new ProtostuffSer());
        client.start();
    }

    public Object getObject() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{ rpcRequest.getInterfaceClass() }, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                RpcFutureResponse futureResponse = new RpcFutureResponse(rpcRequest);
                rpcRequest.setServiceMethod(method.getName());
                rpcRequest.setParameterTypes(method.getParameterTypes());
                rpcRequest.setParameters(args);
                ChannelFuture channelFuture = client.send(rpcRequest);
                channelFuture.sync();
                RpcResponse response = futureResponse.get();
                return response.getResult();
            }
        });
    }
}
