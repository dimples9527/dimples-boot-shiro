package com.dimples.job.task;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/4/8
 */
@Slf4j
@Component
public class TestTask {

    public String test1() {
        log.info("不带参数的test1方法，正在被执行");
        return "test1";
    }
}
