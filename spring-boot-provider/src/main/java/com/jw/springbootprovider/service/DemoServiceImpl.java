package com.jw.springbootprovider.service;

import com.zzht.patrol.screw.provider.annotations.ProviderService;
import org.springframework.stereotype.Service;

@Service
@ProviderService(publishService = DemoService.class)
public class DemoServiceImpl implements DemoService {

    @Override
    public String hello(String msg) {
        System.out.println(msg);
        int i = 1 / 0;
        return msg;
    }
}

