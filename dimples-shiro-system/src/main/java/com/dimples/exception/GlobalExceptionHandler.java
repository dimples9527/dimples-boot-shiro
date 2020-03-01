package com.dimples.exception;

import com.dimples.core.eunm.CodeAndMessageEnum;
import com.dimples.core.exception.BaseExceptionHandler;
import com.dimples.core.transport.DimplesResponse;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.ExpiredSessionException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
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
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler extends BaseExceptionHandler {

    @ExceptionHandler(value = ExpiredSessionException.class)
    public DimplesResponse handleExpiredSessionException(ExpiredSessionException e) {
        log.debug("ExpiredSessionException", e);
        return DimplesResponse.failed(CodeAndMessageEnum.EXPIRED_SESSION.getCode(), e.getMessage());
    }

    /**
     * LockedAccountException
     *
     * @param e LockedAccountException
     * @return ResponseDTO
     */
    @ExceptionHandler(value = LockedAccountException.class)
    public DimplesResponse handleException(LockedAccountException e) {
        log.error("登录验证错误,异常信息: ", e);
        return DimplesResponse.failed(CodeAndMessageEnum.ACCOUNT_LOCK);
    }

    /**
     * UnknownAccountException
     *
     * @param e UnknownAccountException
     * @return ResponseDTO
     */
    @ExceptionHandler(value = UnknownAccountException.class)
    public DimplesResponse handleException(UnknownAccountException e) {
        log.error("登录验证错误,异常信息: ", e);
        return DimplesResponse.failed(CodeAndMessageEnum.USER_NOT_EXISTED);
    }


    /**
     * IncorrectCredentialsException
     *
     * @param e IncorrectCredentialsException
     * @return ResponseDTO
     */
    @ExceptionHandler(value = IncorrectCredentialsException.class)
    public DimplesResponse handleException(IncorrectCredentialsException e) {
        log.error("登录验证错误,异常信息: ", e);
        return DimplesResponse.failed(CodeAndMessageEnum.ACCOUNT_ERROR);
    }
}













