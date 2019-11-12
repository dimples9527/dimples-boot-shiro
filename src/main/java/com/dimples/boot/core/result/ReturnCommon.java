package com.dimples.boot.core.result;

import lombok.Getter;
import lombok.Setter;

/**
 * 通用返回结果类
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/1
 */
public class ReturnCommon {

    @Setter
    @Getter
    private Integer code;

    @Setter
    @Getter
    private String msg;

    @Setter
    @Getter
    private Object data;

    private ReturnCommon() {

    }

    public ReturnCommon(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ReturnCommon(ResultCodeEnum resultCodeEnum, Object data) {
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
    public ReturnCommon(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ReturnCommon(ResultCodeEnum resultCodeEnum) {
        this.code = resultCodeEnum.getCode();
        this.msg = resultCodeEnum.getMsg();
    }

    /**
     * 成功但不带数据
     **/
    public static ReturnCommon success() {
        ReturnCommon result = new ReturnCommon();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMsg(ResultCodeEnum.SUCCESS.getMsg());
        return result;
    }

    public static ReturnCommon success(Object object) {
        ReturnCommon result = new ReturnCommon();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMsg(ResultCodeEnum.SUCCESS.getMsg());
        result.setData(object);
        return result;
    }

    public static ReturnCommon success(ResultCodeEnum resultCodeEnum, Object object) {
        ReturnCommon result = new ReturnCommon();
        result.setCode(resultCodeEnum.getCode());
        result.setMsg(resultCodeEnum.getMsg());
        result.setData(object);
        return result;
    }

    /**
     * 失败
     **/
    public static ReturnCommon error(Integer code, String msg) {
        ReturnCommon result = new ReturnCommon();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static ReturnCommon error(ResultCodeEnum resultCodeEnum) {
        ReturnCommon result = new ReturnCommon();
        result.setCode(resultCodeEnum.getCode());
        result.setMsg(resultCodeEnum.getMsg());
        return result;
    }

    /**
     * 操作失败
     *
     * @return ReturnCommon
     */
    public static ReturnCommon failed() {
        ReturnCommon result = new ReturnCommon();
        result.setCode(ResultCodeEnum.FAIL.getCode());
        result.setMsg(ResultCodeEnum.FAIL.getMsg());
        return result;
    }

    public static ReturnCommon failed(ResultCodeEnum resultCodeEnum) {
        ReturnCommon result = new ReturnCommon();
        result.setCode(resultCodeEnum.getCode());
        result.setMsg(resultCodeEnum.getMsg());
        return result;
    }

}














