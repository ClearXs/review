package com.jw.unittest.message;

import com.jw.unittest.vo.MessageVo;
import lombok.extern.slf4j.Slf4j;

/**
 * 短消息发送的执行器
 * @author jw
 * @date 2021/11/13 9:51
 */
@Slf4j
public class SmsTrigger implements MessageTrigger {

    @Override
    public void run(MessageVo messageVo) {
      log.info("发送短信消息: {}", messageVo);
    }
}
