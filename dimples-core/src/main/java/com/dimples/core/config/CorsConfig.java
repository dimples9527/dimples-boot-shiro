package com.dimples.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域访问控制
 * 做前后分离的话，这个也是必配的
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/18
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    /**
     * 页面跨域访问Controller过滤
     *
     * @param registry CorsRegistry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        WebMvcConfigurer.super.addCorsMappings(registry);
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("*");
    }

}
