package com.jw.basics.netty.rpc.api;

import com.jw.basics.netty.rpc.Demo;

public interface DemoService {

    Demo outDemo(String requestId);
}
