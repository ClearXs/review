package com.jw.unittest.springboottest;

import com.jw.unittest.BaseTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 构建基本的mybatis环境
 * @author jw
 * @date 2021/11/13 15:37
 */
public class ContextTest extends BaseTest {

    protected AnnotationConfigApplicationContext context;

    @BeforeEach
    @Override
    public void init() {
        context = new AnnotationConfigApplicationContext();
        // 向environment注入配置
        TestPropertyValues
                .of(
                        "spring.datasource.url=jdbc:mysql://localhost:3306/bsk?useSSL=false&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&tinyInt1isBit=false&allowMultiQueries=true&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true",
                        "spring.datasource.username=root",
                        "spring.datasource.password=123456",
                        "spring.datasource.driverClassName=com.mysql.cj.jdbc.Driver",
                        "mybatis.mapper-locations=classpath:com/jw/**/mapper/*Mapper.xml",
                        "mybatis.type-aliases-package=com.jw.unittest.entity"
                )
                .applyTo(context);
        context.register(DataSourceAutoConfiguration.class,
                MybatisAutoConfiguration.class, PropertyPlaceholderAutoConfiguration.class,
                MybatisScannerAutoConfiguration.class);
        context.refresh();
    }

    @AfterEach
    @Override
    public void tearDown() {
        if (context != null) {
            context.close();
        }
    }
}
