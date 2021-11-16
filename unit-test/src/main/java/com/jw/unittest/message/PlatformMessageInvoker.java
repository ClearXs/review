package com.jw.unittest.message;

import com.jw.unittest.vo.MessageVo;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

@Slf4j
public class PlatformMessageInvoker implements MessageInvoker {

    private Map<String, MessageTrigger> trggiers;

    public PlatformMessageInvoker() {
        trggiers = new HashMap<>();
        ServiceLoader<MessageTrigger> load = ServiceLoader.load(MessageTrigger.class);
        Iterator<MessageTrigger> triggerIterator = load.iterator();
        while (triggerIterator.hasNext()) {
            MessageTrigger trigger = triggerIterator.next();
            trggiers.put(trigger.getClass().getName(), trigger);
        }
    }

    @Override
    public void invoke(MessageVo messageVo) {
        if (messageVo == null) {
            throw new NullPointerException("message is null");
        }
        MessageTrigger trigger = trggiers.get(messageVo.getType());
        if (trigger == null) {
            throw new NullPointerException("message trigger为空，无法发送消息，查找的消息类型为: " +
                    messageVo.getType());
        }
        trigger.run(messageVo);
    }

    public Map<String, MessageTrigger> getTrggiers() {
        return trggiers;
    }
}
