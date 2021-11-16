package com.jw.spring;

import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertySourceFactory;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * @author jiangw
 */
public class SimplePropertySourceFactory implements PropertySourceFactory {

    @Override
    public PropertySource<?> createPropertySource(String name, EncodedResource resource) throws IOException {
        PropertiesPropertySource propertySource = null;
        try {
            if (name != null) {
                propertySource = new ResourcePropertySource(name, resource);
            } else {
                propertySource = new ResourcePropertySource(resource);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Properties properties = new Properties();
            propertySource = new PropertiesPropertySource("null", properties);
        }
        return propertySource;
    }
}
