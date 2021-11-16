package com.jw.anno;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;

import java.io.File;
import java.io.IOException;

public class GatewayGenerator {

    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException {
        DynamicType.Unloaded<?> make = new ByteBuddy()
                // 创建泛型类
                .subclass(TypeDescription.Generic.Builder.parameterizedType(Repository.class, String.class).build())
                // 添加类信息（这个类名称）
                .name(Repository.class.getPackage().getName().concat(".").concat("CustomizeRepository"))
                .method(ElementMatchers.named("query"))
                .intercept(MethodDelegation.to(GatewayInterceptor.class))
                // 添加方法注解
                .annotateMethod(AnnotationDescription.Builder.ofType(GatewayMethod.class).define("methodName", "生成新的方法").build())
                // 添加类注解
                .annotateType(AnnotationDescription.Builder.ofType(GatewayClass.class).define("desc", "生成新的类").build())
                .make();
        // 输出
        make.saveIn(new File("D://"));
        Repository<String> o = (Repository<String>) make.load(ClassLoader.getSystemClassLoader())
                .getLoaded()
                .newInstance();
        System.out.println(o.query(2));
    }
}
