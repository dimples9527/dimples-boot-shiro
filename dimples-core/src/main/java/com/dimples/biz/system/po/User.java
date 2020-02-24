package com.dimples.biz.system.po;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "com-dimples-biz-system-po-User")
@Data
public class User implements Serializable {
    /**
     * 用户状态：有效
     */
    public static final String STATUS_VALID = "1";
    /**
     * 用户状态：锁定
     */
    public static final String STATUS_LOCK = "0";
    /**
     * 默认的用户密码
     */
    public static final String DEFAULT_PASSWORD = "123456";
    /**
     * 男
     */
    public static final String SEX_MALE = "1";
    /**
     * 女
     */
    public static final String SEX_FEMALE = "0";


    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 登录密码
     */
    @ApiModelProperty(value = "登录密码")
    private String password;

    /**
     * 用户状态，0不启用，1启用，默认为1
     */
    @ApiModelProperty(value = "用户状态，0不启用，1启用，默认为1 ")
    private String status;

    /**
     * 创建日期
     */
    @ApiModelProperty(value = "创建日期")
    private Date createDate;

    /**
     * 更新日期
     */
    @ApiModelProperty(value = "更新日期")
    private Date modifyDate;

    private static final long serialVersionUID = 1L;
}