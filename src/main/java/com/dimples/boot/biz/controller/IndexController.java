package com.dimples.boot.biz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhongyj
 * @date 2019/7/4
 */
@Slf4j
@RestController
@Api(value = "Index类")
public class IndexController {

    @ApiOperation(value = "Index列表", notes = "获取Index列表")
    @GetMapping("/index")
    public String index() {
        return "index";
    }

}
