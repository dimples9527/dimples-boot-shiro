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

@ApiModel(value="com-dimples-biz-system-po-UserInfo")
@Data
@TableName(value = "db_dimples_shiro.tb_user_info")
public class UserInfo implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "user_info_id", type = IdType.AUTO)
    @ApiModelProperty(value="主键id")
    private Long userInfoId;

    /**
     * 用户ID
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value="用户ID")
    private Long userId;

    /**
     * 用户邮箱
     */
    @TableField(value = "email")
    @ApiModelProperty(value="用户邮箱")
    private String email;

    /**
     * 用户电话
     */
    @TableField(value = "mobile")
    @ApiModelProperty(value="用户电话")
    private String mobile;

    /**
     * 上次登录时间
     */
    @TableField(value = "last_login_time")
    @ApiModelProperty(value="上次登录时间")
    private Date lastLoginTime;

    /**
     * 用户头像
     */
    @TableField(value = "avatar")
    @ApiModelProperty(value="用户头像")
    private String avatar;

    /**
     * 性别
     */
    @TableField(value = "gender")
    @ApiModelProperty(value="性别")
    private String gender;

    /**
     * 描述
     */
    @TableField(value = "description")
    @ApiModelProperty(value="描述")
    private String description;

    private static final long serialVersionUID = 1L;
}