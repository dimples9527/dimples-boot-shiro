package com.dimples.boot.core.exception;

import com.dimples.boot.core.result.ResultCodeEnum;
import com.dimples.boot.core.result.ReturnCommon;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常捕获<br>
 *
 * @author zhongyj
 * @date 2019/7/1
 */
@Slf4j
@ControllerAdvice(basePackages = "com.dimples.boot.biz.controller")
@RestControllerAdvice(basePackages = "com.dimples.boot.biz.controller")
public class GlobalExceptionHandler {

    /**
     * 拦截业务异常
     *
     * @param e BizException
     * @return ReturnCommon
     */
    @ExceptionHandler(BizException.class)
    public ReturnCommon bizException(BizException e) {
        log.error("业务异常:" + e.getMessage());
        return ReturnCommon.error(ResultCodeEnum.SERVER_ERROR.getCode(), e.getMessage());
    }

    /**
     * 拦截系统异常
     *
     * @param e Exception
     * @return ReturnCommon
     */
    @ExceptionHandler(RuntimeException.class)
    public ReturnCommon exceptionHandler(RuntimeException e) {
        log.error("系统异常:" + e.getMessage());
        return ReturnCommon.error(ResultCodeEnum.SERVER_ERROR.getCode(), ResultCodeEnum.SERVER_ERROR.getMsg());
    }

}













