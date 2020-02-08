package com.dimples.biz.system.dto;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/2/3
 */
@Data
@ApiModel(value="com-dimples-biz-system-dto-UserDetailDTO")
public class UserDetailDTO {

    @ApiModelProperty(value = "用户id")
    private Long userId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String username;

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

    /**
     * 用户部门
     */
    @ApiModelProperty(value="用户部门")
    private String deptName;

    /**
     * 用户邮箱
     */
    @ApiModelProperty(value="用户邮箱")
    private String email;

    /**
     * 用户电话
     */
    @ApiModelProperty(value="用户电话")
    private String mobile;

    /**
     * 上次登录时间
     */
    @ApiModelProperty(value="上次登录时间")
    private Date lastLoginTime;

    /**
     * 用户头像
     */
    @ApiModelProperty(value="用户头像")
    private String avatar;

    /**
     * 性别
     */
    @ApiModelProperty(value="性别")
    private String gender;

    /**
     * 描述
     */
    @ApiModelProperty(value="描述")
    private String description;
}
