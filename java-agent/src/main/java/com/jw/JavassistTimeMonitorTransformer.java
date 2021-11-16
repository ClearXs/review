package com.jw;

import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.HashSet;
import java.util.Set;

public class JavassistTimeMonitorTransformer implements ClassFileTransformer {

    public static final Set<String> classNames = new HashSet<>();

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        String currentClassName = className.replace("/", ".");
        classNames.add(className);
        try {
            CtClass ctClass = ClassPool.getDefault().get(currentClassName);
            CtMethod[] declaredMethods = ctClass.getDeclaredMethods();
            for (CtMethod declaredMethod : declaredMethods) {
                final StringBuilder source = new StringBuilder();
                source.append("{")
                        // 前置增强
                        .append("long start = System.nanoTime();\n")
                        // 调用原有方法
                        .append("$_ = $proceed($$);\n")
                        // 后置增强
                        .append("System.out.print(\"method:[")
                        .append(declaredMethod.getName()).append("]\");").append("\n")
                        .append("System.out.println(\" cost:[\" +(System.nanoTime() - start)+ \"ns]\");")
                        .append("}");
                ExprEditor exprEditor = new ExprEditor() {
                    @Override
                    public void edit(MethodCall m) throws CannotCompileException {
                        m.replace(source.toString());
                    }
                };
                declaredMethod.instrument(exprEditor);
                return ctClass.toBytecode();
            }
        } catch (NotFoundException | CannotCompileException | IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
