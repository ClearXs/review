package com.jw.websocket.message;

/**
 * 消息的抽象实体，在客户端需要按照明确的字段类型才能进行映射接收
 * 序列化采取json
 * 它的思路是针对不同类型消息采取不同类型的实体实体
 * @author jw
 * @date 2021/11/17 9:21
 */
public interface Message {

    String AUTH_REQUEST_MESSAGE = "auth-request-message";

}
