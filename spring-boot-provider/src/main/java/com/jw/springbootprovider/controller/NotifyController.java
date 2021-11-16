package com.jw.springbootprovider.controller;

import com.jw.springbootprovider.service.DemoService;
import com.jw.springbootprovider.service.DemoServiceImpl;
import com.zzht.patrol.screw.common.exception.ConnectionException;
import com.zzht.patrol.screw.provider.Notifier;
import com.zzht.patrol.screw.spring.ScrewSpringProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/tests")
@RestController
public class NotifyController {

    @Autowired
    private DemoService demoService;

    @Autowired
    private ScrewSpringProvider screwSpringProvider;

    @GetMapping("/notify")
    public void notifyListener(String msg) throws NoSuchMethodException, ConnectionException {
        String hello = demoService.hello(msg);
        Notifier notifier = new Notifier(screwSpringProvider.getProvider());
        notifier.unicast(hello, DemoServiceImpl.class, "hello", new Class<?>[]{String.class});
    }
}
