package com.dimples.core.properties;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/1/21
 */

@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:dimples.properties"})
@ConfigurationProperties(prefix = "dimples")
public class DimplesProperties {

    private ShiroProperties shiro = new ShiroProperties();
    private boolean autoOpenBrowser = true;
    private SwaggerProperties swagger = new SwaggerProperties();

    private int maxBatchInsertNum = 1000;

    private ValidateCodeProperties code = new ValidateCodeProperties();

}
