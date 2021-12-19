package com.jw.spring;

import com.jw.spring.event.CustomService;
import com.jw.spring.event.EventConfig;
import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.Proxy;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import java.lang.reflect.Method;
import java.util.Map;

public class ApplicationContextTest {

    @Test
    public void simple() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(LifecycleConfig.class, DemoConfig.class);
        Demo demo1 = applicationContext.getBean("demo1", Demo.class);
        String name = demo1.getName();
        System.out.println(name);

        Map<String, String> beansOfType = applicationContext.getBeansOfType(String.class);
        for (Map.Entry<String, String> entry : beansOfType.entrySet()) {
            System.out.println(entry);
        }
    }

    @Test
    public void testXml() {
        ApplicationContext applicationContext = new GenericXmlApplicationContext("/spring-beans.xml");
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = applicationContext.getBean(beanDefinitionName);
            System.out.println(bean);
        }
        Demo bean = applicationContext.getBean(Demo.class);
        Map<String, String> beansOfType = applicationContext.getBeansOfType(String.class);
        for (Map.Entry<String, String> entry : beansOfType.entrySet()) {
            System.out.println(entry);
        }
        PropertyPlaceholderConfigurerExt corePlaceHolder = (PropertyPlaceholderConfigurerExt) applicationContext.getBean("corePlaceHolder");
        String test = corePlaceHolder.getValue("test");
        System.out.println(test);
    }

    @Test
    public void testLifecycle() throws Exception {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(LifecycleConfig.class, IBeanPostProcessor.class);

        LifecycleDemo bean = applicationContext.getBean(LifecycleDemo.class);
        System.out.println(bean);

        Demo bean1 = applicationContext.getBean(Demo.class);
        Person person = applicationContext.getBean(Person.class);

        System.out.println(bean1);
        applicationContext.destroy();
    }

    @Test
    public void testProxy() {
        Class<Person> personClass = Person.class;
        Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{personClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return method.invoke(proxy, args);
            }
        });
        Person cast = personClass.cast(personClass);
    }

    @Test
    public void testEvent() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(EventConfig.class);
        CustomService bean = applicationContext.getBean(CustomService.class);
        bean.notifyEvent();
    }

    @Test
    public void testContext() {
        ApplicationContext applicationContext = new GenericXmlApplicationContext("/spring-context.xml");
        Demo bean = applicationContext.getBean(Demo.class);
        System.out.println(bean.getName());
    }
}
