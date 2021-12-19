package com.jw.websocket;

import com.jw.websocket.message.Message;

public interface Handler {

    void handle(Message message);

    String getType();
}
