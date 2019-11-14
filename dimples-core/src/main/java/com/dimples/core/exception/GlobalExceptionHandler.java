package com.dimples.core.exception;

import com.dimples.core.result.ResultCodeEnum;
import com.dimples.core.result.ResultCommon;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
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
@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 拦截业务异常
     *
     * @param e BizException
     * @return ResultCommon
     */
    @ExceptionHandler(BizException.class)
    public ResultCommon bizException(BizException e) {
        log.error("业务异常:" + e.getMessage());
        return ResultCommon.error(ResultCodeEnum.SERVER_ERROR.getCode(), e.getMessage());
    }

    /**
     * 身份验证失败
     *
     * @param e AuthenticationException
     * @return ResultCommon
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResultCommon authenticationException(AuthenticationException e) {
        log.error("身份校验失败: {}", e.getMessage());
        return ResultCommon.error(ResultCodeEnum.UNAUTHORIZED);
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    public ResultCommon handleUnauthorizedException(UnauthorizedException e) {
        log.error("未授权", e);
        return ResultCommon.error(HttpStatus.FORBIDDEN.value(), e.getMessage());
    }

    @ExceptionHandler(value = AuthorizationException.class)
    public ResultCommon handleAuthorizationException(AuthorizationException e){
        log.error("没有该权限", e);
        return ResultCommon.error(HttpStatus.UNAUTHORIZED.value(),e.getMessage());
    }

    /**
     * 拦截系统异常
     *
     * @param e Exception
     * @return ResultCommon
     */
    @ExceptionHandler(RuntimeException.class)
    public ResultCommon exceptionHandler(RuntimeException e) {
        log.error("系统异常:" + e.getMessage());
        return ResultCommon.error(ResultCodeEnum.SERVER_ERROR.getCode(), ResultCodeEnum.SERVER_ERROR.getMsg());
    }

}













