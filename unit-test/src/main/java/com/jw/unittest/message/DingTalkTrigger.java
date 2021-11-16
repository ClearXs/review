package com.jw.unittest.message;

import com.jw.unittest.vo.MessageVo;
import lombok.extern.slf4j.Slf4j;

/**
 * dingding发送消息的执行器
 * @author jw
 * @date 2021/11/13 9:51
 */
@Slf4j
public class DingTalkTrigger implements MessageTrigger {

    @Override
    public void run(MessageVo messageVo) {
        log.info("发送叮叮消息: {}", messageVo);
    }
}
