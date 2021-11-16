package com.jw.springbootdatax.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "datax")
@ToString
@Setter
@Getter
public class DataXConfig {

    private boolean enable;

    private String[] jobFile;

    private String jobCorn;

    private String dataxPath;

    private String checkSql;
}
