package com.jw;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.pool.TypePool;

import java.io.File;
import java.io.IOException;

public class ModifyClass {

    public static void main(String[] args) throws IOException {
        // 修改Proxy类，生成字节码到指定文件下
        new ByteBuddy()
                .redefine(Proxy.class)
                .make()
                .saveIn(new File("D://"));

        // 读取外部文件、jar、网络重新生成新的Class文件
        ClassFileLocator locator = ClassFileLocator.ForJarFile.of(new File("D:/java-agent-1.0.jar"));
        TypePool typePool = TypePool.Default.of(locator);
        new ByteBuddy()
                .redefine(typePool.describe("com.jw.Out").resolve(), locator)
                .make()
                .load(ClassLoader.getSystemClassLoader());
    }
}
