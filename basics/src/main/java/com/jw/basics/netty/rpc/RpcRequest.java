package com.jw.basics.netty.rpc;

import lombok.Data;

@Data
public class RpcRequest {

    private String requestId;

    private String serviceName;

    private Class<?> interfaceClass;

    private String serviceMethod;

    private Class<?>[] parameterTypes;

    private Object[] parameters;
}
