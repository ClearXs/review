package com.jw.websocket.message;

import lombok.Data;

/**
 * 用于验证的请求消息，由客户端发送。
 * 它的token是调用系统接口的认证接口进行获取，在web socket中会再次验证
 * @author jw
 * @date 2021/11/17 9:26
 */
@Data
public class AuthRequest implements Message {

    private String token;

}
