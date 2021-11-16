package com.jw.basics.netty.rpc.api;

import com.jw.basics.netty.rpc.Demo;

public class DemoServiceImpl implements DemoService {
    @Override
    public Demo outDemo(String requestId) {
        Demo demo = new Demo();
        demo.setName("2121" + requestId);
        return demo;
    }
}
