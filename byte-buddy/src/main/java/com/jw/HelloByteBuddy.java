package com.jw;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FixedValue;
import net.bytebuddy.matcher.ElementMatchers;

public class HelloByteBuddy {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException {
        ByteBuddy byteBuddy = new ByteBuddy();
        Class<?> loaded = byteBuddy
                .subclass(Object.class)
                .method(ElementMatchers.named("toString"))
                .intercept(FixedValue.value("Hello World!"))
                .make()
                .load(Proxy.class.getClassLoader())
                .getLoaded();
        String s = loaded.newInstance().toString();
        System.out.println(s);
    }
}
