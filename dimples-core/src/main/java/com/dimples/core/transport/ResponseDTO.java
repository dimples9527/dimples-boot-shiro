package com.dimples.core.transport;

import com.alibaba.fastjson.JSONObject;
import com.dimples.core.eunm.CodeAndMessageEnum;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 通用返回结果类
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/1
 */
@SuppressWarnings("all")
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO<T> {

    @Setter
    @Getter
    private Integer code;

    @Setter
    @Getter
    private String msg;

    @Setter
    @Getter
    private T data;

    /**
     * 成功但不带数据
     **/
    public static ResponseDTO success() {
        ResponseDTO result = new ResponseDTO();
        result.setCode(CodeAndMessageEnum.SUCCESS.getCode());
        result.setMsg(CodeAndMessageEnum.SUCCESS.getMessage());
        return result;
    }

    /**
     * 成功带数据
     **/
    public static ResponseDTO success(Object object) {
        ResponseDTO result = new ResponseDTO();
        result.setCode(CodeAndMessageEnum.SUCCESS.getCode());
        result.setMsg(CodeAndMessageEnum.SUCCESS.getMessage());
        result.setData(object);
        return result;
    }

    /**
     * 操作失败
     *
     * @return ResponseDTO
     */
    public static ResponseDTO failed() {
        ResponseDTO result = new ResponseDTO();
        result.setCode(CodeAndMessageEnum.FAIL.getCode());
        result.setMsg(CodeAndMessageEnum.FAIL.getMessage());
        return result;
    }

    /**
     * 失败
     **/
    public static ResponseDTO failed(Integer code, String msg) {
        ResponseDTO result = new ResponseDTO();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public static ResponseDTO failed(String message) {
        ResponseDTO result = new ResponseDTO();
        result.setCode(CodeAndMessageEnum.SERVER_ERROR.getCode());
        result.setMsg(message);
        return result;
    }

    public static ResponseDTO failed(CodeAndMessageEnum resultCodeEnum) {
        ResponseDTO result = new ResponseDTO();
        result.setCode(resultCodeEnum.getCode());
        result.setMsg(resultCodeEnum.getMessage());
        return result;
    }

    /**
     * 泛型数据返回
     *
     * @param object T
     * @return ResponseDTO<T>
     */
    public ResponseDTO<T> ok(T object) {
        ResponseDTO result = new ResponseDTO();
        result.setCode(CodeAndMessageEnum.SUCCESS.getCode());
        result.setMsg(CodeAndMessageEnum.SUCCESS.getMessage());
        result.setData(object);
        return result;
    }

    public ResponseDTO<T> ok(CodeAndMessageEnum resultCodeEnum, T object) {
        ResponseDTO result = new ResponseDTO();
        result.setCode(resultCodeEnum.getCode());
        result.setMsg(resultCodeEnum.getMessage());
        result.setData(object);
        return result;
    }

    public ResponseDTO<T> error(String msg) {
        ResponseDTO result = new ResponseDTO();
        result.setCode(CodeAndMessageEnum.FAIL.getCode());
        result.setMsg(msg);
        return result;
    }

    /**
     * 设置响应
     *
     * @param response    HttpServletResponse
     * @param contentType content-type
     * @param status      http状态码
     * @param value       响应内容
     * @throws IOException IOException
     */
    public static void makeResponse(HttpServletResponse response, String contentType,
                                    int status, Object value) throws IOException {
        response.setContentType(contentType);
        response.setStatus(status);
        response.getOutputStream().write(JSONObject.toJSONString(value).getBytes());
    }

}