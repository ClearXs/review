package com.jw.websocket;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultHandlerMapping implements HandlerMapping {

    private Map<String, Handler> handlers = new ConcurrentHashMap<>();

    @Override
    public void register(String handlerType, Handler handler) {
        handlers.putIfAbsent(handlerType, handler);
    }

    @Override
    public Handler getHandler(Session session) {
        return null;
    }
}
