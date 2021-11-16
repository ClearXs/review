package com.jw.spring;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;

import java.util.List;

public class Property implements ApplicationContextAware {

    @Value("${test}")
    private String test;

    @Autowired
    private LifecycleConfig lifecycleConfig;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ConfigurableEnvironment environment = (ConfigurableEnvironment) applicationContext.getEnvironment();
        PropertySource<?> app = environment.getPropertySources().get("app");
        System.out.println(environment);

    }

    public static class SimpleBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

        private List<Class<?>> simpleBeans;

        public SimpleBeanDefinitionRegistryPostProcessor(List<Class<?>> simpleBeans) {
            this.simpleBeans = simpleBeans;
        }

        @Override
        public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
            for (Class<?> simpleBean : simpleBeans) {
                BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(simpleBean);
                    GenericBeanDefinition beanDefinition = (GenericBeanDefinition) builder.getRawBeanDefinition();
                    beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(simpleBean);
                    beanDefinition.setBeanClass(SimpleFactoryBean.class);
                    beanDefinition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
                    registry.registerBeanDefinition(simpleBean.getSimpleName(), beanDefinition);
            }
        }

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        }

    }
}
