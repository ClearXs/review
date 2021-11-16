package com.jw.unittest.message;

import com.jw.unittest.vo.MessageVo;

/**
 * 能够触发消息发送的接口，每个消息的执行器由Spi进行实现
 * @author jw
 * @date 2021/11/13 9:50
 */
public interface MessageTrigger {

    void run(MessageVo messageVo);
}
