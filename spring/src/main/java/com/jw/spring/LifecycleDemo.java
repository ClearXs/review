package com.jw.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;

public class LifecycleDemo implements BeanNameAware, BeanClassLoaderAware,
        BeanFactoryAware, ApplicationContextAware, InitializingBean, DisposableBean {

    private String name;

    public LifecycleDemo() {
        System.out.println("perform constructor");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("perform setter");
        this.name = name;
    }

    public void init() {
        System.out.println("perform init");
    }

    public void postConstruct() {
        System.out.println("perform postConstruct");
    }

    @Override
    public String toString() {
        return "LifecycleDemo{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("perform setBeanName: " + name);
        this.name = name;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("perform setBeanClassLoader: " + classLoader);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("perform setBeanFactory: " + beanFactory);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ConfigurableEnvironment environment = (ConfigurableEnvironment) applicationContext.getEnvironment();
        MutablePropertySources propertySources = environment.getPropertySources();

        System.out.println("perform setApplicationContext: " + applicationContext);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("perform afterPropertiesSet");
    }

    public void preDestroy() {
        System.out.println("preform preDestroy");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("preform destroy");
    }

    public void destroyMethod() {
        System.out.println("preform destroyMethod");
    }
}
