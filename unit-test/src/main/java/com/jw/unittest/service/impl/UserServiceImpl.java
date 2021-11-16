package com.jw.unittest.service.impl;

import com.jw.unittest.mapper.UserMapper;
import com.jw.unittest.entity.User;
import com.jw.unittest.message.Invoker;
import com.jw.unittest.service.UserService;
import com.jw.unittest.util.ContextUtil;
import com.jw.unittest.vo.MessageVo;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Data
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    /**
     * 消息发送调用器
     */
    private Invoker invoker;

    /**
     * 发送消息线程池
     */
    private final ExecutorService launcher;

    public UserServiceImpl() {
        launcher = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors(),
                1000,
                TimeUnit.MICROSECONDS,
                new LinkedBlockingQueue<>());
    }

    @Override
    public void asyncSendMessage(MessageVo messageVo) {
        launcher.submit(() -> {
            if (userMapper == null || invoker == null) {
                log.error("发送消息失败");
                throw new NullPointerException("发送消息失败");
            }
            String currentUserId = ContextUtil.getCurrentUserId();
            User user = userMapper.get(currentUserId);
            messageVo.setSource(user);
            invoker.invoke(messageVo);
        });
    }

}
