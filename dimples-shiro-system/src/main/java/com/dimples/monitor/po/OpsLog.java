package com.dimples.monitor.po;

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
 * @date 2020/3/31
 */
@ApiModel(value = "com-dimples-monitor-po-OpsLog")
@Data
@TableName(value = "db_dimples_shiro.tb_ops_log")
public class OpsLog implements Serializable {
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键id")
    private Long id;

    /**
     * 操作用户
     */
    @TableField(value = "username")
    @ApiModelProperty(value = "操作用户")
    private String username;

    /**
     * 对应操作
     */
    @TableField(value = "operation")
    @ApiModelProperty(value = "对应操作")
    private String operation;

    /**
     * 次数
     */
    @TableField(value = "time")
    @ApiModelProperty(value = "操作耗时")
    private String time;

    /**
     * 操作方法
     */
    @TableField(value = "method")
    @ApiModelProperty(value = "操作方法")
    private String method;

    /**
     * 参数
     */
    @TableField(value = "params")
    @ApiModelProperty(value = "参数")
    private String params;

    /**
     * 操作ip
     */
    @TableField(value = "ip")
    @ApiModelProperty(value = "操作ip")
    private String ip;

    /**
     * 操作时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 地址
     */
    @TableField(value = "location")
    @ApiModelProperty(value = "地址")
    private String location;

    private transient String createTimeFrom;
    private transient String createTimeTo;

    private static final long serialVersionUID = 1L;
}