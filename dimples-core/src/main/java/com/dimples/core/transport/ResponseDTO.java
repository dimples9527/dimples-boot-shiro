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
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {

    @Setter
    @Getter
    private Integer code;

    @Setter
    @Getter
    private String message;

    @Setter
    @Getter
    private Object data;

    /**
     * 成功但不带数据
     **/
    public static ResponseDTO success() {
        ResponseDTO result = new ResponseDTO();
        result.setCode(CodeAndMessageEnum.SUCCESS.getCode());
        result.setMessage(CodeAndMessageEnum.SUCCESS.getMessage());
        return result;
    }

    /**
     * 成功带数据
     **/
    public static ResponseDTO success(Object object) {
        ResponseDTO result = new ResponseDTO();
        result.setCode(CodeAndMessageEnum.SUCCESS.getCode());
        result.setMessage(CodeAndMessageEnum.SUCCESS.getMessage());
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
        result.setMessage(CodeAndMessageEnum.FAIL.getMessage());
        return result;
    }

    /**
     * 失败
     **/
    public static ResponseDTO failed(Integer code, String msg) {
        ResponseDTO result = new ResponseDTO();
        result.setCode(code);
        result.setMessage(msg);
        return result;
    }

    public static ResponseDTO failed(String message) {
        ResponseDTO result = new ResponseDTO();
        result.setCode(CodeAndMessageEnum.SERVER_ERROR.getCode());
        result.setMessage(message);
        return result;
    }

    public static ResponseDTO failed(CodeAndMessageEnum resultCodeEnum) {
        ResponseDTO result = new ResponseDTO();
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
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