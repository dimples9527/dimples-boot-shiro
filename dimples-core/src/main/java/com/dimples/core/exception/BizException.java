package com.dimples.core.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * 自定义异常
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/7
 */
@Getter
@Setter
public class BizException extends RuntimeException {

    /**
     * 异常信息
     */
    private String message;

    /**
     * 异常状态码
     */
    private int code;

    public BizException(String msg) {
        super(msg);
        this.message = msg;
    }

    public BizException(String msg, Throwable e) {
        super(msg, e);
        this.message = msg;
    }

    public BizException(String msg, int code) {
        super(msg);
        this.message = msg;
        this.code = code;
    }

    public BizException(String msg, int code, Throwable e) {
        super(msg, e);
        this.message = msg;
        this.code = code;
    }

    public BizException(BizExceptionEnum bizExceptionEnum) {
        super(bizExceptionEnum.getMessage());
        this.code = bizExceptionEnum.getCode();
        this.message = bizExceptionEnum.getMessage();
    }

    public BizException(BizExceptionEnum bizExceptionEnum, Throwable e) {
        super(bizExceptionEnum.getMessage(), e);
        this.code = bizExceptionEnum.getCode();
        this.message = bizExceptionEnum.getMessage();
    }

}













