package com.dimples.system.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@ApiModel(value="com-dimples-biz-system-po-UserDept")
@Data
@TableName(value = "db_dimples_shiro.tb_user_dept")
public class UserDept implements Serializable {
    /**
     * 用户科室关联id
     */
    @TableId(value = "user_dept_id", type = IdType.AUTO)
    @ApiModelProperty(value="用户科室关联id")
    private Long userDeptId;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value="用户id")
    private Long userId;

    /**
     * 科室id
     */
    @TableField(value = "dept_id")
    @ApiModelProperty(value="科室id")
    private Long deptId;

    private static final long serialVersionUID = 1L;
}