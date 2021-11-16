package com.jw.asm;

import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class HelloAsm {

    public static void main(String[] args) {
        ClassWriter classWriter = new ClassWriter(0);
        // 定义对象头：版本号、修饰符、全类名、签名、父类、实现的接口
        classWriter.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "com/jw/asm/HelloAsm", null, "java/lang/Object", null);
        // 添加方法：修饰符、方法名、描述符、签名、异常
        MethodVisitor main = classWriter.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC, "main", "([Ljava/lang/string;)V", null, null);
        // 执行指令，获取静态属性
        main.visitFieldInsn(Opcodes.GETSTATIC, "java/io/PrintStream", "out", "Ljava/io/PrintStream");
        // 加载常量
        main.visitLdcInsn("Hello World");
        // 调用方法
        main.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        // 返回
        main.visitInsn(Opcodes.RETURN);
        // 设置操作数栈的深度和局部变量的大小
        main.visitMaxs(2, 1);
        // 方法结束
        main.visitEnd();
        // 类完成
        classWriter.visitEnd();
        // 生成字节数组
    }

}
