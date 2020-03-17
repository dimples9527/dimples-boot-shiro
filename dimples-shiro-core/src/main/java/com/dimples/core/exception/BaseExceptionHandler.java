package com.dimples.core.exception;

import com.dimples.core.eunm.CodeAndMessageEnum;
import com.dimples.core.transport.DimplesResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;

import lombok.extern.slf4j.Slf4j;

/**
 * 基础异常捕捉
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/12/27
 */
@Slf4j
public class BaseExceptionHandler {

    /**
     * BizException
     *
     * @param e BizException
     * @return ResponseDTO
     */
    @ExceptionHandler(value = BizException.class)
    public DimplesResponse handleBizException(BizException e) {
        log.error("业务错误", e);
        return DimplesResponse.failed(e.getMessage());
    }

    @ExceptionHandler(value = DataException.class)
    public DimplesResponse handleBizException(DataException e) {
        return DimplesResponse.failed(e.getCode(), e.getMessage());
    }

    /**
     * AccessDeniedException
     *
     * @return ResponseDTO
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public DimplesResponse handleAccessDeniedException() {
        return DimplesResponse.failed(CodeAndMessageEnum.NOT_AUTH);
    }

    /**
     * 没有权限
     *
     * @param e UnauthorizedException
     * @return DimplesResponse
     */
    @ExceptionHandler(value = UnauthorizedException.class)
    public DimplesResponse handleUnauthorizedException(UnauthorizedException e) {
        log.error("UnauthorizedException", e);
        return DimplesResponse.failed(CodeAndMessageEnum.NOT_FOUND);
    }

    /**
     * 认证失败
     *
     * @param e AuthenticationException
     * @return DimplesResponse
     */
    @ExceptionHandler(value = AuthenticationException.class)
    public DimplesResponse handleAuthenticationException(AuthenticationException e) {
        log.error("AuthenticationException", e);
        return DimplesResponse.failed(CodeAndMessageEnum.UNAUTHORIZED);
    }

    /**
     * 权限校验
     *
     * @param e AuthorizationException
     * @return DimplesResponse
     */
    @ExceptionHandler(value = AuthorizationException.class)
    public DimplesResponse handleAuthorizationException(AuthorizationException e) {
        log.error("AuthorizationException", e);
        return DimplesResponse.failed(CodeAndMessageEnum.NOT_FOUND);
    }

    /**
     * 统一处理请求参数校验(普通传参)
     *
     * @param e ConstraintViolationException
     * @return ResponseDTO
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public DimplesResponse handleConstraintViolationException(ConstraintViolationException e) {
        StringBuilder message = new StringBuilder();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            Path path = violation.getPropertyPath();
            String[] pathArr = StringUtils.splitByWholeSeparatorPreserveAllTokens(path.toString(), ".");
            message.append(pathArr[1]).append(violation.getMessage()).append(",");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        return DimplesResponse.failed(CodeAndMessageEnum.REQUEST_INVALIDATE.getCode(), message.toString());
    }

    /**
     * 统一处理请求参数校验(实体对象传参)
     *
     * @param e BindException
     * @return ResponseDTO
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DimplesResponse handleBindException(BindException e) {
        StringBuilder message = new StringBuilder();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            message.append(error.getField()).append(error.getDefaultMessage()).append(",");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        return DimplesResponse.failed(message.toString());
    }

    /**
     * Exception
     *
     * @param e Exception
     * @return ResponseDTO
     */
    @ExceptionHandler(value = Exception.class)
    public DimplesResponse handleException(Exception e) {
        log.error("系统内部异常,异常信息: ", e);
        return DimplesResponse.failed(CodeAndMessageEnum.FAIL);
    }

}














