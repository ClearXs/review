package com.jw.spring;

import org.springframework.beans.factory.FactoryBean;

public class SimpleFactoryBean<T> implements FactoryBean<T> {

    private Class<T> clazz;

    public SimpleFactoryBean() {
    }

    public SimpleFactoryBean(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T getObject() throws Exception {
        return clazz.newInstance();
    }

    @Override
    public Class<?> getObjectType() {
        return clazz;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

}
