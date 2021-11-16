package com.jw.spring;

import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@PropertySource(name = "app", value = {
        "classpath:prop/app1.properties"
}, factory = SimplePropertySourceFactory.class)
@Order(2)
public class LifecycleConfig {

    @Bean(initMethod = "init", destroyMethod = "destroyMethod")
    public LifecycleDemo lifecycle() {
        LifecycleDemo lifecycleDemo = new LifecycleDemo();
        lifecycleDemo.setName("21");
        System.out.println("lifecycle");
        return lifecycleDemo;
    }

    @Bean
    public Property property() {
        System.out.println("Property");
        return new Property();
    }

    @Bean
    public <T extends Object> BeanDefinitionRegistryPostProcessor simpleRegister() {
        List<Class<?>> classes = Arrays.asList(Demo.class, Person.class);
        Property.SimpleBeanDefinitionRegistryPostProcessor processor = new Property.SimpleBeanDefinitionRegistryPostProcessor(classes);
        return processor;
    }

}
