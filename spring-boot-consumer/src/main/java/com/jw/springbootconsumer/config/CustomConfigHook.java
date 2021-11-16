package com.jw.springbootconsumer.config;

import com.alibaba.fastjson.JSONObject;
import com.zzht.patrol.screw.spring.config.Property;
import com.zzht.patrol.screw.spring.config.hook.ConfigHook;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Properties;

@Component
public class CustomConfigHook implements ConfigHook {

    @Override
    public void dataSourceLoader(JSONObject generic, Properties properties, ApplicationContext applicationContext) {
        properties.setProperty("spring.datasource.url", generic.getString("url"));
        properties.setProperty("spring.datasource.username", generic.getString("datasourceUsername"));
        properties.setProperty("spring.datasource.password", generic.getString("datasourcePassword"));
        properties.setProperty("spring.datasource.driver-class-name", generic.getString("driverClassName"));
        PropertiesPropertySource propertySource = new PropertiesPropertySource("spring.datasource", properties);
        Property.addProperties(propertySource, applicationContext.getEnvironment());
    }
}
