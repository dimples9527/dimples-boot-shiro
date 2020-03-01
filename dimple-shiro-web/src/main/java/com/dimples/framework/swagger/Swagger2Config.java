package com.dimples.framework.swagger;

import com.dimples.core.properties.DimplesProperties;
import com.dimples.core.properties.SwaggerProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger配置<br>
 *
 * @author zhongyj
 * @date 2019/7/4
 */
@Slf4j
@Configuration
@EnableSwagger2
public class Swagger2Config {

    private DimplesProperties properties;

    @Autowired
    public Swagger2Config(DimplesProperties properties) {
        this.properties = properties;
    }

    @Bean
    public Docket createRestApi() {
        log.info("===================================== 开启 Swagger2 配置 =====================================");
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        SwaggerProperties swagger = properties.getSwagger();
        Contact contact = new Contact(swagger.getAuthor(), swagger.getUrl(), swagger.getEmail());
        return new ApiInfoBuilder()
                .title(swagger.getTitle())
                .description(swagger.getDescription())
                .termsOfServiceUrl("127.0.0.1")
                .contact(contact)
                .version(swagger.getVersion())
                .build();
    }

}



























