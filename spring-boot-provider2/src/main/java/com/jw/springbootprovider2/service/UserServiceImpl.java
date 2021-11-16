package com.jw.springbootprovider2.service;

import com.zzht.patrol.screw.common.exception.ConnectionException;
import com.zzht.patrol.screw.common.metadata.ServiceMetadata;
import com.zzht.patrol.screw.consumer.ConnectionWatcher;
import com.zzht.patrol.screw.consumer.NettyConsumer;
import com.zzht.patrol.screw.consumer.model.ProxyObjectFactory;
import com.zzht.patrol.screw.provider.annotations.ProviderService;
import com.zzht.patrol.screw.spring.ScrewSpringConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@ProviderService(publishService = UserService.class)
public class UserServiceImpl implements UserService {

    private ScrewSpringConsumer screwSpringConsumer;

    @Override
    public Map<String, Object> getUserInfo() throws ConnectionException, InterruptedException {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "21");
        map.put("age", 21);
        ServiceMetadata serviceMetadata = new ServiceMetadata("provider1");
        NettyConsumer consumer = screwSpringConsumer.getConsumer();
        ConnectionWatcher connectWatch = consumer.watchConnect(serviceMetadata);
        Object o = ProxyObjectFactory
                .factory()
                .consumer(consumer)
                .metadata(serviceMetadata)
                .isAsync(false)
                .connectWatch(connectWatch)
                .remoteInvoke("DemoService", "hello", new Object[]{"232321"});
        map.put("msg", o);
        return map;
    }
}
