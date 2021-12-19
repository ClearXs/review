package com.jw.websocket.endpoint;

import com.jw.websocket.DefaultHandlerMapping;
import com.jw.websocket.Handler;
import com.jw.websocket.HandlerMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNullApi;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@Controller
@ServerEndpoint("/")
@Slf4j
public class WebServerEndpoint implements InitializingBean, ApplicationContextAware {

    private ApplicationContext applicationContext;

    private HandlerMapping handlerMapping = new DefaultHandlerMapping();

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        log.info("打开连接");
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        log.info("接收到一条消息：[{}]", message);
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        log.info("关闭连接，关闭原因：[{}]", reason);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        log.info("发生异常：[{}]", throwable.getMessage());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        applicationContext.getBeansOfType(Handler.class)
                .values()
                .forEach(handler -> handlerMapping.register(handler.getType(), handler));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
