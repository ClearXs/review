package com.jw;

import net.bytebuddy.ByteBuddy;

import java.io.File;
import java.io.IOException;

public class GenerateClass {

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, IOException {

        // 定义一个Generate的类
        Object o = new ByteBuddy()
                .subclass(Object.class)
                .name("com.jw.Generate")
                .make()
                .load(ClassLoader.getSystemClassLoader())
                .getLoaded()
                .newInstance();


        // 将定义类生成字节码输出到指定文件查看
        new ByteBuddy()
                .subclass(Object.class)
                .name("com.jw.Generate")
                .make()
                .saveIn(new File("D://"));
    }
}
