package com.dimples.core.exception;

import com.dimples.core.eunm.CodeAndMessageEnum;
import com.dimples.core.transport.ResponseDTO;

import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

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
    public ResponseDTO handleBizException(BizException e) {
        log.error("业务错误", e);
        return ResponseDTO.failed(e.getMessage());
    }

    /**
     * AccessDeniedException
     *
     * @return ResponseDTO
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public ResponseDTO handleAccessDeniedException() {
        return ResponseDTO.failed(CodeAndMessageEnum.NOT_AUTH);
    }

    /**
     * Exception
     *
     * @param e Exception
     * @return ResponseDTO
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseDTO handleException(Exception e) {
        log.error("系统内部异常,异常信息: ", e);
        return ResponseDTO.failed(CodeAndMessageEnum.FAIL);
    }

}