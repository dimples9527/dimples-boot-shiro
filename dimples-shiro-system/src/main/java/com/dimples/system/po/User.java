package com.dimples.system.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/3/1
 */
@ApiModel(value = "com-dimples-biz-system-po-User")
@Data
@TableName(value = "db_dimples_shiro.tb_user")
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
    @TableId(value = "user_id", type = IdType.AUTO)
    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 用户名
     */
    @TableField(value = "username")
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 登录密码
     */
    @TableField(value = "password")
    @ApiModelProperty(value = "登录密码")
    private String password;

    /**
     * 用户状态，0不启用，1启用，默认为1
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "用户状态，0不启用，1启用，默认为1 ")
    private String status;

    /**
     * 创建日期
     */
    @TableField(value = "create_date")
    @ApiModelProperty(value = "创建日期")
    private Date createDate;

    /**
     * 更新日期
     */
    @TableField(value = "modify_date")
    @ApiModelProperty(value = "更新日期")
    private Date modifyDate;

    private static final long serialVersionUID = 1L;

    public static final String USER_ID = "user_id";
}