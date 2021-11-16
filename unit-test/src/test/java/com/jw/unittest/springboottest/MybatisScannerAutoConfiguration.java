package com.jw.unittest.springboottest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@MapperScan(basePackages = "com.jw.**.mapper")
public class MybatisScannerAutoConfiguration {
}
