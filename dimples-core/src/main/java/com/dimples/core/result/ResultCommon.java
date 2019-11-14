package com.dimples.core.result;

import com.dimples.core.eunm.CodeAndMessageEnum;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

/**
 * 通用返回结果类
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/1
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultCommon {

    @Setter
    @Getter
    private Integer code;

    @Setter
    @Getter
    private String msg;

    @Setter
    @Getter
    private Object data;

    private ResultCommon() {

    }

    public ResultCommon(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultCommon(CodeAndMessageEnum resultCodeEnum, Object data) {
        this.code = resultCodeEnum.getCode();
        this.msg = resultCodeEnum.getMessage();
        this.data = data;
    }

    /**
     * 此构造方法在全局异常处理器中用到
     *
     * @param code 异常的状态码，如：401（身份验证失败）、500（服务器内部错误）
     * @param msg  异常的提示信息
     */
    public ResultCommon(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResultCommon(CodeAndMessageEnum resultCodeEnum) {
        this.code = resultCodeEnum.getCode();
        this.msg = resultCodeEnum.getMessage();
    }

    /**
     * 成功但不带数据
     **/
    public static ResultCommon success() {
        ResultCommon result = new ResultCommon();
        result.setCode(CodeAndMessageEnum.SUCCESS.getCode());
        result.setMsg(CodeAndMessageEnum.SUCCESS.getMessage());
        return result;
    }

    public static ResultCommon success(Object object) {
        ResultCommon result = new ResultCommon();
        result.setCode(CodeAndMessageEnum.SUCCESS.getCode());
        result.setMsg(CodeAndMessageEnum.SUCCESS.getMessage());
        result.setData(object);
        return result;
    }

    public static ResultCommon success(CodeAndMessageEnum resultCodeEnum, Object object) {
        ResultCommon result = new ResultCommon();
        result.setCode(resultCodeEnum.getCode());
        result.setMsg(resultCodeEnum.getMessage());
        result.setData(object);
        return result;
    }

    /**
     * 失败
     **/
    public static ResultCommon error(Integer code, String msg) {
        ResultCommon result = new ResultCommon();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static ResultCommon error(CodeAndMessageEnum resultCodeEnum) {
        ResultCommon result = new ResultCommon();
        result.setCode(resultCodeEnum.getCode());
        result.setMsg(resultCodeEnum.getMessage());
        return result;
    }

    /**
     * 操作失败
     *
     * @return ResultCommon
     */
    public static ResultCommon failed() {
        ResultCommon result = new ResultCommon();
        result.setCode(CodeAndMessageEnum.FAIL.getCode());
        result.setMsg(CodeAndMessageEnum.FAIL.getMessage());
        return result;
    }

    public static ResultCommon failed(CodeAndMessageEnum resultCodeEnum) {
        ResultCommon result = new ResultCommon();
        result.setCode(resultCodeEnum.getCode());
        result.setMsg(resultCodeEnum.getMessage());
        return result;
    }

}














