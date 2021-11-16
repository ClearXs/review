package com.jw.unittest.service;

import com.jw.unittest.vo.MessageVo;

public interface UserService {

    /**
     * 异步发送消息
     * @param messageVo
     */
    void asyncSendMessage(MessageVo messageVo);
}
