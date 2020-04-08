package com.dimples.job.po;

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
 * @date 2020/4/8
 */
@ApiModel(value = "com-dimples-job-po-JobLog")
@Data
@TableName(value = "db_dimples_shiro.tb_job_log")
public class JobLog implements Serializable {

    /**
     * 任务执行成功
     */
    public static final String JOB_SUCCESS = "0";
    /**
     * 任务执行失败
     */
    public static final String JOB_FAIL = "1";

    /**
     * 任务日志id
     */
    @TableId(value = "log_id", type = IdType.AUTO)
    @ApiModelProperty(value = "任务日志id")
    private Long logId;

    /**
     * 任务id
     */
    @TableField(value = "job_id")
    @ApiModelProperty(value = "任务id")
    private Long jobId;

    /**
     * spring bean名称
     */
    @TableField(value = "bean_name")
    @ApiModelProperty(value = "spring bean名称")
    private String beanName;

    /**
     * 方法名
     */
    @TableField(value = "method_name")
    @ApiModelProperty(value = "方法名")
    private String methodName;

    /**
     * 参数
     */
    @TableField(value = "params")
    @ApiModelProperty(value = "参数")
    private String params;

    /**
     * 任务状态    0：成功    1：失败
     */
    @TableField(value = "status")
    @ApiModelProperty(value = "任务状态    0：成功    1：失败")
    private String status;

    /**
     * 失败信息
     */
    @TableField(value = "error")
    @ApiModelProperty(value = "失败信息")
    private String error;

    /**
     * 耗时(单位：毫秒)
     */
    @TableField(value = "times")
    @ApiModelProperty(value = "耗时(单位：毫秒)")
    private Long times;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    private static final long serialVersionUID = 1L;
}