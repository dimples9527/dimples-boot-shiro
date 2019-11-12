package com.dimples.core.biz.controller;

import com.dimples.core.core.annotation.OpsLog;
import com.dimples.core.core.eunm.OpsLogTypeEnum;
import com.dimples.core.core.exception.BizException;
import com.dimples.core.core.exception.BizExceptionEnum;

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
    @OpsLog(value = "test", type = {OpsLogTypeEnum.SELECT})
    @GetMapping("/index")
    public String index() throws BizException {
        String result;
        try {
            result = "test";
            throw new  BizException(BizExceptionEnum.REQUEST_NULL);
        } catch (BizException e){
            throw new  BizException(BizExceptionEnum.REQUEST_NULL);
        }
    }

}
