package com.dimples.boot.core.result;

/**
 * 通用返回结果类
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/1
 */
public class ReturnCommon {

    private Integer code;
    private String msg;
    private Object data;

    private ReturnCommon() {

    }

    public ReturnCommon(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
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

    public static ReturnCommon success(Object object) {
        ReturnCommon result = new ReturnCommon();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMsg(ResultCodeEnum.SUCCESS.getMsg());
        result.setData(object);
        return result;
    }

    /**
     * 成功但不带数据
     **/
    public static ReturnCommon success() {
        return ReturnCommon.success(null);
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


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}














