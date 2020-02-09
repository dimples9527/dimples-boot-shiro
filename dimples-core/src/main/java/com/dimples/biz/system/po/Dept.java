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

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/2/8
 */
@ApiModel(value = "com-dimples-biz-system-po-Dept")
@Data
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

    /**
     * 父级id
     */
    @TableField(value = "parent_id")
    @ApiModelProperty(value = "父级id")
    private Long parentId;

    /**
     * 同级顺序
     */
    @TableField(value = "order_num")
    @ApiModelProperty(value = "同级顺序")
    private Long orderNum;

    private static final long serialVersionUID = 1L;
}