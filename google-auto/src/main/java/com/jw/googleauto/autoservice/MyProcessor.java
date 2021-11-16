package com.jw.googleauto.autoservice;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.*;

/**
 * Todo
 * @author jw
 * @date 2021/10/30 9:47
 */
@SupportedAnnotationTypes("com.jw.googleauto.autoservice.TestAnnotation")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedOptions("debug")
@AutoService(Processor.class)
public class MyProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(TestAnnotation.class);
        elements.forEach(this::log);
        return false;
    }

    private void log(Object msg) {
        if (processingEnv.getOptions().containsKey("debug")) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "my test processor: " + msg.toString());
        }
    }
}
