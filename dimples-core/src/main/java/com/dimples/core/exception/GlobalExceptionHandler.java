package com.dimples.core.exception;

import com.dimples.core.eunm.CodeAndMessageEnum;
import com.dimples.core.transport.DimplesResponse;

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

}













