package com.dimples.job.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/4/6
 */
@ApiModel(value = "com-dimples-job-po-Job")
@Data
@TableName(value = "db_dimples_shiro.tb_job")
public class Job implements Serializable {
    /**
     * 任务id
     */
    @TableId(value = "job_id", type = IdType.AUTO)
    @ApiModelProperty(value = "任务id")
    private Long jobId;

    /**
     * spring bean名称
     */
    @NotBlank(message = "beanName不能为空")
    @TableField(value = "bean_name")
    @ApiModelProperty(value = "spring bean名称")
    private String beanName;

    /**
     * 方法名
     */
    @NotBlank(message = "methodName不能为空")
    @TableField(value = "method_name")
    @ApiModelProperty(value = "方法名")
    private String methodName;

    /**
     * 参数
     */
    @Size(max = 50, message = "params大小不能超过50")
    @TableField(value = "params")
    @ApiModelProperty(value = "参数")
    private String params;

    /**
     * cron表达式
     */
    @NotBlank(message = "cronExpression不能为空")
    @TableField(value = "cron_expression")
    @ApiModelProperty(value = "cron表达式")
    private String cronExpression;

    /**
     * 任务状态  0：正常  1：暂停
     */
    @Size(max = 100, message = "status不能为空")
    @TableField(value = "status")
    @ApiModelProperty(value = "任务状态  0：正常  1：暂停")
    private String status;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    private static final long serialVersionUID = -5287338371089552841L;

    /**
     * 任务调度参数 key
     */
    public static final String JOB_PARAM_KEY = "JOB_PARAM_KEY";

    public enum ScheduleStatus {
        /**
         * 正常
         */
        NORMAL("0"),
        /**
         * 暂停
         */
        PAUSE("1");

        private String value;

        ScheduleStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    private transient String createTimeFrom;
    private transient String createTimeTo;
}