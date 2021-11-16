package com.jw.spring;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;
import org.springframework.util.CollectionUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public class PackageScanTest {

    @Test
    public void simpleScan() throws IOException, InstantiationException, IllegalAccessException {
        String name = Configurable.class.getName();
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resourcePatternResolver.getResources("classpath*:com\\jw\\spring\\*");
        MetadataReaderFactory metadata = new SimpleMetadataReaderFactory();
        for (Resource resource : resources) {
           try {
               MetadataReader metadataReader = metadata.getMetadataReader(resource);
               AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
               Map<String, Object> annotationAttributes = annotationMetadata.getAnnotationAttributes(Configurable.class.getName());
               if (!CollectionUtils.isEmpty(annotationAttributes)) {
                   String className = annotationMetadata.getClassName();
                   Class<?> aClass = Class.forName(className);
               }
               System.out.println(annotationAttributes);
           } catch (FileNotFoundException | ClassNotFoundException e) {
               e.printStackTrace();
           }
        }
    }

}
