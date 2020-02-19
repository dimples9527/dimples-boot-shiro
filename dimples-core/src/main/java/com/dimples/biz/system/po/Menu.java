package com.dimples.biz.system.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(value = "com-dimples-biz-system-po-Menu")
@Data
@TableName(value = "db_dimples_shiro.tb_menu")
public class Menu implements Serializable {
    /**
     * 菜单
     */
    public static final String TYPE_MENU = "0";
    /**
     * 按钮
     */
    public static final String TYPE_BUTTON = "1";
    /**
     * 顶级ID
     */
    public static final Long TOP_NODE = 0L;
    /**
     * 类型 0 菜单 1按钮
     */
    @TableId(value = "menu_id", type = IdType.AUTO)
    @ApiModelProperty(value = "类型 0 菜单 1按钮")
    private Long menuId;

    /**
     * 上级菜单ID
     */
    @TableField(value = "parent_id")
    @ApiModelProperty(value = "上级菜单ID")
    private Long parentId;

    /**
     * 菜单/按钮名称
     */
    @TableField(value = "menu_name")
    @ApiModelProperty(value = "菜单/按钮名称")
    private String menuName;

    /**
     * 对应路由path
     */
    @TableField(value = "href")
    @ApiModelProperty(value = "对应路由path")
    private String href;

    /**
     * 对应路由组件component
     */
    @TableField(value = "component")
    @ApiModelProperty(value = "对应路由组件component")
    private String component;

    /**
     * 权限标识
     */
    @TableField(value = "perms")
    @ApiModelProperty(value = "权限标识")
    private String perms;

    /**
     * 图标
     */
    @TableField(value = "icon")
    @ApiModelProperty(value = "图标")
    private String icon;

    /**
     * 类型 0菜单 1按钮
     */
    @TableField(value = "type")
    @ApiModelProperty(value = "类型 0菜单 1按钮")
    private String type;

    /**
     * 排序
     */
    @TableField(value = "order_num")
    @ApiModelProperty(value = "排序")
    private Integer orderNum;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "modify_time")
    @ApiModelProperty(value = "修改时间")
    private Date modifyTime;

    private static final long serialVersionUID = 1L;
}