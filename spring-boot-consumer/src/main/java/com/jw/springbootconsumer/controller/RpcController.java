package com.jw.springbootconsumer.controller;

import com.zzht.patrol.screw.common.exception.ConnectionException;
import com.zzht.patrol.screw.common.exception.InvokeFutureException;
import com.zzht.patrol.screw.common.future.FutureListener;
import com.zzht.patrol.screw.common.future.InvokeFuture;
import com.zzht.patrol.screw.common.future.InvokeFutureContext;
import com.zzht.patrol.screw.common.metadata.ServiceMetadata;
import com.zzht.patrol.screw.consumer.ConnectionWatcher;
import com.zzht.patrol.screw.consumer.NettyConsumer;
import com.zzht.patrol.screw.consumer.model.ProxyObjectFactory;
import com.zzht.patrol.screw.spring.ScrewSpringConsumer;
import com.zzht.patrol.screw.spring.anntation.ScrewValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RpcController {

    private ScrewSpringConsumer screwSpringConsumer;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private AutowiredAnnotationBeanPostProcessor processor;

    @ScrewValue("address-address.defaultCfg.cityName")
    private String address;

    @ScrewValue("address-address.types[0].name")
    private String name;

    @ScrewValue("redis-redis.port")
    private String screwPort;

    @Value("${remember.me.cookie.name}")
    private String auth;


    @GetMapping("/syncRpc")
    public Object syncRpc(String msg) throws ConnectionException, InterruptedException {
        ServiceMetadata serviceMetadata = new ServiceMetadata("provider1");
        NettyConsumer consumer = screwSpringConsumer.getConsumer();
        ConnectionWatcher connectWatch = consumer.watchConnect(serviceMetadata);
        Object o = ProxyObjectFactory
                .factory()
                .consumer(consumer)
                .metadata(serviceMetadata)
                .isAsync(false)
                .connectWatch(connectWatch)
                .remoteInvoke("DemoService", "hello", new Object[]{msg});
        System.out.println(o);
        return o;
    }

    @GetMapping("/getUserInfo")
    public Object getUserInfo() throws ConnectionException, InterruptedException {
        ServiceMetadata serviceMetadata = new ServiceMetadata("provider2");
        NettyConsumer consumer = screwSpringConsumer.getConsumer();
        ConnectionWatcher connectWatch = consumer.watchConnect(serviceMetadata);
        Object o = ProxyObjectFactory
                .factory()
                .consumer(consumer)
                .metadata(serviceMetadata)
                .isAsync(false)
                .connectWatch(connectWatch)
                .remoteInvoke("UserService", "getUserInfo", null);
        System.out.println(o);
        return o;
    }

    @GetMapping("/asyncRpc")
    public Object asyncRpc(String msg) throws ConnectionException, InterruptedException, InvokeFutureException {
        ServiceMetadata serviceMetadata = new ServiceMetadata("provider1");
        NettyConsumer consumer = screwSpringConsumer.getConsumer();
        ConnectionWatcher connectWatch = consumer.watchConnect(serviceMetadata);
        ProxyObjectFactory
                .factory()
                .consumer(consumer)
                .metadata(serviceMetadata)
                .isAsync(true)
                .connectWatch(connectWatch)
                .remoteInvoke("DemoService", "hello", new Object[]{msg});
        InvokeFuture<String> stringInvokeFuture = InvokeFutureContext.get(String.class);
        stringInvokeFuture.addListener(new FutureListener<String>() {
            @Override
            public void completed(String s, Throwable throwable) throws Exception {
                System.out.println(s);
            }
        });
        return null;
    }

    @GetMapping("/rpcNotFound")
    public Object rpcNotFound(String msg) throws ConnectionException, InterruptedException {
        ServiceMetadata serviceMetadata = new ServiceMetadata("provider1");
        NettyConsumer consumer = screwSpringConsumer.getConsumer();
        ConnectionWatcher connectWatch = consumer.watchConnect(serviceMetadata);
        Object o = ProxyObjectFactory
                .factory()
                .consumer(consumer)
                .metadata(serviceMetadata)
                .isAsync(false)
                .connectWatch(connectWatch)
                .remoteInvoke("DemoService", "rpcNotFound", new Object[]{msg});
        System.out.println(o);
        return o;
    }

    @GetMapping("/test")
    public Object test() {
        System.out.println(address);
        System.out.println(name);
        System.out.println(auth);
        return address;
    }
}
