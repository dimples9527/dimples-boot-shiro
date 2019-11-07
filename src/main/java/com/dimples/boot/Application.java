package com.dimples.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

/**
 * 程序入口<br>
 *
 * @author zhongyj
 * @date 2019/7/1
 */
@Slf4j
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("==================== 程序已成功启动 ====================");
    }

}
