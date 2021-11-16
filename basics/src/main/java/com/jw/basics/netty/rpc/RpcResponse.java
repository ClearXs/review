package com.jw.basics.netty.rpc;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class RpcResponse {

    private String requestId;

    private Object result;

    private String message;

    private boolean isSuccess;
}
