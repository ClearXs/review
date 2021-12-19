package com.jw.websocket;

import javax.websocket.Session;

/**
 * 参考自{@link org.springframework.web.servlet.HandlerMapping}
 * <p>对于一种消息类型都会</p>
 * @author jw
 * @date 2021/11/17 9:30
 */
public interface HandlerMapping {

    Handler getHandler(Session session);

    void register(String handlerType, Handler handler);

}
