package com.jw.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.*;
import org.springframework.core.io.support.ResourcePropertySource;

import java.util.Properties;

@Configurable
@PropertySource(name = "app", value = {
        "classpath:prop/app1.properties"
}, factory = SimplePropertySourceFactory.class)
@Order(0)
public class DemoConfig implements ApplicationContextAware, InitializingBean {

    @Value("${test}")
    private String test;

    private ApplicationContext applicationContext;

    @Bean
    public Demo demo1() {
        Demo demo = new Demo();
        demo.setName("21");
        System.out.println("demo1加载");
        return demo;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("DemoConfig");
        if (applicationContext instanceof ConfigurableApplicationContext) {
            ConfigurableApplicationContext demoConfig = (ConfigurableApplicationContext) applicationContext;
            ConfigurableListableBeanFactory beanFactory = demoConfig.getBeanFactory();
            BeanDefinition demo01 = beanFactory.getBeanDefinition("demo1");
            System.out.println(demo01);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Properties properties = new Properties();
        properties.setProperty("test", "test");
        PropertiesPropertySource propertiesPropertySource = new PropertiesPropertySource("test", properties);
        this.applicationContext = applicationContext;
        addProperties(propertiesPropertySource, applicationContext.getEnvironment());

    }

    private void addProperties(PropertiesPropertySource propertySource, Environment environment) {
        String name = propertySource.getName();
        MutablePropertySources propertySources = ((ConfigurableEnvironment) environment).getPropertySources();
        if (propertySources.contains(name)) {
            // We've already added a version, we need to extend it
            org.springframework.core.env.PropertySource<?> existing = propertySources.get(name);
            org.springframework.core.env.PropertySource<?> newSource = (propertySource instanceof ResourcePropertySource ?
                    ((ResourcePropertySource) propertySource).withResourceName() : propertySource);
            if (existing instanceof CompositePropertySource) {
                ((CompositePropertySource) existing).addFirstPropertySource(newSource);
            }
            else {
                if (existing instanceof ResourcePropertySource) {
                    existing = ((ResourcePropertySource) existing).withResourceName();
                }
                CompositePropertySource composite = new CompositePropertySource(name);
                composite.addPropertySource(newSource);
                composite.addPropertySource(existing);
                propertySources.replace(name, composite);
            }
        } else {
            if (name == null) {
                propertySources.addFirst(propertySource);
            } else {
                propertySources.addLast(propertySource);
            }
        }
    }
}
