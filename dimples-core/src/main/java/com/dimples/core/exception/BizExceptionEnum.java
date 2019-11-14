package com.dimples.core.exception;

/**
 * 业务异常的枚举
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/7
 */
public enum BizExceptionEnum {

    /**
     * 文件上传
     */
    FILE_READING_ERROR(400, "文件读取错误"),
    // 文件不存在
    FILE_NOT_FOUND(400, "文件不存在"),
    // 上传图片出错
    UPLOAD_ERROR(500, "上传图片出错"),
    // 上传文件出错
    UPLOAD_FILE_ERROR(500, "上传文件出错"),

    /**
     * 权限和数据问题
     */
    // 数据库中没有该资源
    DB_RESOURCE_NULL(400, "数据库中没有该资源"),
    // 权限异常
    NO_PERMISSION(403, "权限异常"),
    // 请求数据格式不正确
    REQUEST_INVALIDATE(400, "请求数据格式不正确"),
    // 验证码不正确
    INVALID_KAPTCHA(400, "验证码不正确"),
    // 不能删除超级管理员
    CANT_DELETE_ADMIN(600, "不能删除超级管理员"),
    // 不能冻结超级管理员
    CANT_FREEZE_ADMIN(600, "不能冻结超级管理员"),
    // 不能修改超级管理员角色
    CANT_CHANGE_ADMIN(600, "不能修改超级管理员角色"),

    /**
     * 账户问题
     */
    // 该用户已经注册
    USER_ALREADY_REG(401, "该用户已经注册"),
    // 没有此用户
    NO_THIS_USER(400, "没有此用户"),
    // 没有此用户
    USER_NOT_EXISTED(400, "没有此用户"),
    // 账号被冻结
    ACCOUNT_FREEZED(401, "账号被冻结"),
    // 原密码不正确
    OLD_PWD_NOT_RIGHT(402, "原密码不正确"),
    // 两次输入密码不一致
    TWO_PWD_NOT_MATCH(405, "两次输入密码不一致"),
    //新密码为空
    NEW_PWD_EMPTY(405, "新密码不能为空"),

    /**
     * 错误的请求
     */
    // 请求有错误
    REQUEST_NULL(400, "请求有错误"),
    // 会话超时
    SESSION_TIMEOUT(400, "会话超时"),
    // 服务器异常
    SERVER_ERROR(500, "服务器异常");

    BizExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
