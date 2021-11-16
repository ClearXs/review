package com.jw;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.FieldAccessor;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;

public class FiledSetterGetter {

    public static void main(String[] args) throws IOException {
        new ByteBuddy()
                .subclass(Proxy.class)
                .defineField("name", String.class, Modifier.PUBLIC)
                .implement(NameInterceptor.class)
                .intercept(FieldAccessor.ofBeanProperty())
                .make()
                .saveIn(new File("D://"));
    }
}
