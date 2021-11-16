package com.jw.reactor.webflux.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ReactiveAdapterRegistry;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.accept.RequestedContentTypeResolver;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurationSupport;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
public class WebFluxConfiguration extends WebFluxConfigurationSupport {

//    @Bean
//    @Override
//    public ResponseBodyResultHandlerWrapper responseBodyResultHandler(ReactiveAdapterRegistry reactiveAdapterRegistry,
//                                                               ServerCodecConfigurer serverCodecConfigurer,
//                                                               RequestedContentTypeResolver contentTypeResolver) {
//        return new ResponseBodyResultHandlerWrapper(serverCodecConfigurer.getWriters(),
//                contentTypeResolver, reactiveAdapterRegistry);
//    }

    /**
     * 添加全局的 CORS 配置
     * @param registry
     */
    @Override
    protected void addCorsMappings(CorsRegistry registry) {

        // 匹配所有URL
        registry.addMapping("/**")
                // 允许所有请求来源
                .allowedOrigins("*")
                // 允许发送 Cookie
                .allowCredentials(true)
                // // 允许所有请求 Method
                .allowedMethods("*")
                // 允许所有请求 Header
                .allowedHeaders("*")
                // 允许所有响应 Header
//                .exposedHeaders("*")
                // 有效期 1800 秒，2 小时
                .maxAge(1800L);
    }

    @Component
    @Order(Integer.MIN_VALUE)
    public static class LogWebFilter implements WebFilter {

        Logger logger = LoggerFactory.getLogger(getClass());

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
            logger.info("global external filter");
            return chain.filter(exchange);
        }
    }
}
