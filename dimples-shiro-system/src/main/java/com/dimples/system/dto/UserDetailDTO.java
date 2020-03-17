package com.dimples.system.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.dimples.system.po.User;
import com.dimples.system.po.UserInfo;

import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/2/3
 */
@Data
@ApiModel(value = "com-dimples-biz-system-dto-UserDetailDTO")
public class UserDetailDTO implements Serializable {

    private static final long serialVersionUID = -5400704441650059140L;

    /**
     * 默认头像
     */
    public static final String DEFAULT_AVATAR = "/dimples/images/avatar/default.jpg";


    @ApiModelProperty(value = "用户id")
    @ExcelProperty(value = "用户id")
    private Long userId;

    /**
     * 用户名
     */
    @NotBlank(message = "username 不能为空")
    @ApiModelProperty(value = "用户名")
    @ExcelProperty(value = "用户名")
    private String username;

    /**
     * 用户状态，0不启用，1启用，默认为1
     */
    @ApiModelProperty(value = "用户状态，0不启用，1启用，默认为1 ")
    @ExcelProperty(value = "用户状态")
    private String status;

    /**
     * 创建日期
     */
    @ApiModelProperty(value = "创建日期")
    @ExcelProperty(value = "创建日期")
    private Date createDate;

    /**
     * 更新日期
     */
    @ApiModelProperty(value = "更新日期")
    @ExcelProperty(value = "更新日期")
    private Date modifyDate;

    /**
     * 用户部门
     */
    @ApiModelProperty(value = "用户部门Id")
    private String deptId;

    /**
     * 用户部门
     */
    @ApiModelProperty(value = "用户部门")
    private String deptName;

    /**
     * 用户邮箱
     */
    @ApiModelProperty(value = "用户邮箱")
    @ExcelProperty(value = "用户邮箱")
    private String email;

    /**
     * 用户电话
     */
    @ApiModelProperty(value = "用户电话")
    @ExcelProperty(value = "用户电话")
    private String mobile;

    /**
     * 上次登录时间
     */
    @ApiModelProperty(value = "上次登录时间", hidden = true)
    @ExcelProperty(value = "上次登录时间")
    private Date lastLoginTime;

    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像")
    @ExcelProperty(value = "用户头像")
    private String avatar;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    @ExcelProperty(value = "性别")
    private String gender;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    @ExcelProperty(value = "描述")
    private String description;

    /**
     * 角色 ID
     */
    @ApiModelProperty(value = "角色 ID")
    private String roleId;

    /**
     * 角色
     */
    @ApiModelProperty(value = "角色")
    private String roleName;

    public static User convertUser(UserDetailDTO userDetailDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDetailDTO, user);
        return user;
    }

    public static UserInfo convertUserInfo(UserDetailDTO userDetailDTO) {
        UserInfo user = new UserInfo();
        BeanUtils.copyProperties(userDetailDTO, user);
        return user;
    }

}
