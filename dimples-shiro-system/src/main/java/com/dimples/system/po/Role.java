package com.dimples.system.po;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/2/5
 */
@ApiModel(value = "com-dimples-biz-system-po-Role")
@Data
@TableName(value = "db_dimples_shiro.tb_role")
public class Role implements Serializable {
    /**
     * 角色id
     */
    @TableId(value = "role_id", type = IdType.AUTO)
    @ApiModelProperty(value = "角色id")
    private Long roleId;

    /**
     * 角色名称
     */
    @TableField(value = "role_name")
    @ApiModelProperty(value = "角色名称")
    @NotBlank(message = "roleName 不能为空")
    private String roleName;

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

    /**
     * 角色描述
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "角色描述")
    private String remark;

    /**
     * 角色对应的菜单（按钮） id
     */
    @ExcelIgnore
    @TableField(exist = false)
    @NotBlank(message = "menuIds 不能为空")
    @ApiModelProperty(value = "菜单id(多个以|分隔)")
    private String menuIds;

    private static final long serialVersionUID = 1L;
}