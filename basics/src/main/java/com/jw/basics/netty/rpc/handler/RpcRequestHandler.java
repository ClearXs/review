package com.jw.basics.netty.rpc.handler;

import com.jw.basics.netty.rpc.RpcRequest;
import com.jw.basics.netty.rpc.RpcResponse;
import com.jw.basics.netty.rpc.ServiceProvider;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;

public class RpcRequestHandler extends SimpleChannelInboundHandler<RpcRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest rpcRequest) throws Exception {
        // 调用服务
        String serviceName = rpcRequest.getServiceName();
        String serviceMethod = rpcRequest.getServiceMethod();
        Object[] parameters = rpcRequest.getParameters();
        Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
        Class<?> service = ServiceProvider.getInstance().getService(serviceName);
        Object target = service.newInstance();
        Method method = service.getMethod(serviceMethod, parameterTypes);
        method.setAccessible(true);
        Object result = method.invoke(target, parameters);
        // 拼装response
        RpcResponse response = new RpcResponse();
        try {
            response.setSuccess(true);
            response.setMessage("调用成功");
            response.setRequestId(rpcRequest.getRequestId());
            response.setResult(result);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.getMessage());
        }
        ctx.channel().writeAndFlush(response);
    }
}
