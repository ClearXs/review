package com.jw.websocket;

import com.jw.websocket.message.Message;
import org.springframework.stereotype.Component;

@Component
public class AuthHandler implements Handler {

    @Override
    public void handle(Message message) {

    }

    @Override
    public String getType() {
        return Message.AUTH_REQUEST_MESSAGE;
    }
}
