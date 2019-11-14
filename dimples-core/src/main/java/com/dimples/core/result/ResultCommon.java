package com.dimples.core.result;

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

    public ResultCommon(ResultCodeEnum resultCodeEnum, Object data) {
        this.code = resultCodeEnum.getCode();
        this.msg = resultCodeEnum.getMsg();
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

    public ResultCommon(ResultCodeEnum resultCodeEnum) {
        this.code = resultCodeEnum.getCode();
        this.msg = resultCodeEnum.getMsg();
    }

    /**
     * 成功但不带数据
     **/
    public static ResultCommon success() {
        ResultCommon result = new ResultCommon();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMsg(ResultCodeEnum.SUCCESS.getMsg());
        return result;
    }

    public static ResultCommon success(Object object) {
        ResultCommon result = new ResultCommon();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMsg(ResultCodeEnum.SUCCESS.getMsg());
        result.setData(object);
        return result;
    }

    public static ResultCommon success(ResultCodeEnum resultCodeEnum, Object object) {
        ResultCommon result = new ResultCommon();
        result.setCode(resultCodeEnum.getCode());
        result.setMsg(resultCodeEnum.getMsg());
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

    public static ResultCommon error(ResultCodeEnum resultCodeEnum) {
        ResultCommon result = new ResultCommon();
        result.setCode(resultCodeEnum.getCode());
        result.setMsg(resultCodeEnum.getMsg());
        return result;
    }

    /**
     * 操作失败
     *
     * @return ResultCommon
     */
    public static ResultCommon failed() {
        ResultCommon result = new ResultCommon();
        result.setCode(ResultCodeEnum.FAIL.getCode());
        result.setMsg(ResultCodeEnum.FAIL.getMsg());
        return result;
    }

    public static ResultCommon failed(ResultCodeEnum resultCodeEnum) {
        ResultCommon result = new ResultCommon();
        result.setCode(resultCodeEnum.getCode());
        result.setMsg(resultCodeEnum.getMsg());
        return result;
    }

}














