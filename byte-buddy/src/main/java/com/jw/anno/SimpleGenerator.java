package com.jw.anno;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.annotation.AnnotationDescription;
import net.bytebuddy.dynamic.DynamicType;

import java.io.File;
import java.io.IOException;

public class SimpleGenerator {

    public static void main(String[] args) throws IOException {
        DynamicType.Unloaded<Object> make = new ByteBuddy()
                .subclass(Object.class)
                .name(SimpleClass.class.getPackage().getName().concat(".").concat("Simple"))
                .annotateType(AnnotationDescription.Builder.ofType(SimpleClass.class).define("name", "name").build())
                .make();
        make.saveIn(new File("D://"));
    }
}
