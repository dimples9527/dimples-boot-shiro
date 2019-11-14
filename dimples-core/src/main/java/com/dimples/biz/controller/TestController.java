package com.dimples.biz.controller;

import com.dimples.core.annotation.OpsLog;
import com.dimples.core.eunm.CodeAndMessageEnum;
import com.dimples.core.eunm.OpsLogTypeEnum;
import com.dimples.core.exception.BizException;
import com.dimples.core.result.ResultCommon;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
@Api(value = "测试模块", tags = "测试模块")
@RequestMapping("/test")
public class TestController {

    @ApiOperation(value = "测试异常", notes = "测试异常")
    @OpsLog(value = "测试异常", type = {OpsLogTypeEnum.TEST})
    @GetMapping("/exception")
    public String exception() throws BizException {
        throw new BizException(CodeAndMessageEnum.REQUEST_NULL);
    }

    @ApiOperation(value = "测试自定义日志注解", notes = "测试自定义日志注解")
    @OpsLog(value = "测试自定义日志注解", type = {OpsLogTypeEnum.TEST})
    @GetMapping("/log")
    public ResultCommon log() {
        return ResultCommon.success();
    }

}
