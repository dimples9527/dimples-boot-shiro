package com.dimples.core.biz.controller;

import com.dimples.core.core.annotation.OpsLog;
import com.dimples.core.core.eunm.OpsLogTypeEnum;
import com.dimples.core.core.exception.BizException;
import com.dimples.core.core.exception.BizExceptionEnum;
import com.dimples.core.core.result.ResultCommon;

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
@Api(value = "Index类")
@RequestMapping("/test")
public class TestController {

    @ApiOperation(value = "测试异常", notes = "测试异常")
    @OpsLog(value = "测试异常", type = {OpsLogTypeEnum.TEST})
    @GetMapping("/exception")
    public String exception() throws BizException {
        throw new BizException(BizExceptionEnum.REQUEST_NULL);
    }

    @ApiOperation(value = "测试自定义日志注解", notes = "测试自定义日志注解")
    @OpsLog(value = "测试自定义日志注解", type = {OpsLogTypeEnum.TEST})
    @GetMapping("/log")
    public ResultCommon log() {
        return ResultCommon.success();
    }

}
