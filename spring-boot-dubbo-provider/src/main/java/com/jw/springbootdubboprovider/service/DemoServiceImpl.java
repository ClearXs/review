package com.jw.springbootdubboprovider.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = DemoService.class)
public class DemoServiceImpl implements DemoService {

    @Override
    public String hello() {
        return "hello";
    }
}
