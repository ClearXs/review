package com.jw.spring;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class PropertyPlaceholderConfigurerExt extends PropertyPlaceholderConfigurer {
    private static final Map<String, String> properties = new HashMap();

    public PropertyPlaceholderConfigurerExt() {
    }

    @Override
    protected void convertProperties(Properties var1) {
        Iterator var2 = var1.stringPropertyNames().iterator();

        while(var2.hasNext()) {
            String var3 = (String)var2.next();
            String var4 = var1.getProperty(var3);
            properties.put(var3, var4);
        }

        super.convertProperties(var1);
    }

    public String getValue(String var1) {
        return (String)properties.get(var1);
    }
}