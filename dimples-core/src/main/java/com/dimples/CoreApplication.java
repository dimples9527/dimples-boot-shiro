package com.dimples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

/**
 * 启动类
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/12
 */
@Slf4j
@SpringBootApplication
public class CoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
        log.info("============================ 程序启动 ============================");
    }

}
