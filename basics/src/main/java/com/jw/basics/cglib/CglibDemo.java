package com.jw.basics.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibDemo {

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Real.class);
        enhancer.setCallback(new MyInterceptor());
        Real o = (Real) enhancer.create();
        System.out.println(o.add(2, 2));
    }

    interface Real {
        int add(int x, int y);
    }

    static class RealClass implements Real {

        @Override
        public int add(int x, int y) {
            return x + y;
        }
    }


    static class MyInterceptor implements MethodInterceptor {

        @Override
        public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
            if (method.getReturnType() == int.class) {
                return proxy.invokeSuper(obj, args);
            }
            return proxy.invoke(obj, args);
        }
    }

}
