package com.dimples.core.exception;

import com.dimples.core.eunm.CodeAndMessageEnum;
import com.dimples.core.transport.DimplesResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
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














