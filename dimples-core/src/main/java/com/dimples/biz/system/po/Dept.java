package com.dimples.biz.system.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/1/30
 */
@ApiModel(value = "com-dimples-biz-system-po-Dept")
@Data
@Builder
@TableName(value = "db_dimples_shiro.tb_dept")
public class Dept implements Serializable {
    /**
     * 科室id
     */
    @TableId(value = "dept_id", type = IdType.AUTO)
    @ApiModelProperty(value = "科室id")
    private Long deptId;

    /**
     * 科室名称
     */
    @TableField(value = "dept_name")
    @ApiModelProperty(value = "科室名称")
    private String deptName;

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
}