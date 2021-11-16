package com.jw;

import net.bytebuddy.ByteBuddy;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;

public class FieldOperation {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchFieldException, IOException {
        Proxy proxy = new ByteBuddy()
                .subclass(Proxy.class)
                .defineField("name", String.class, Modifier.PUBLIC)
                .make()
                .load(ClassLoader.getSystemClassLoader())
                .getLoaded()
                .newInstance();
        System.out.println(proxy.getClass().getDeclaredField("name"));

        // 输出到磁盘
        new ByteBuddy()
                .subclass(Proxy.class)
                .defineField("name", String.class, Modifier.PUBLIC)
                .make().saveIn(new File("D://"));
    }
}
