package com.dimples.biz.system.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@ApiModel(value="com-dimples-biz-system-po-RoleUser")
@Data
@TableName(value = "db_dimples_shiro.tb_role_user")
public class RoleUser implements Serializable {
    /**
     * 角色职位关联id
     */
    @TableId(value = "role_user_id", type = IdType.AUTO)
    @ApiModelProperty(value="角色职位关联id")
    private Long roleUserId;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value="用户id")
    private Long userId;

    /**
     * 角色id
     */
    @TableField(value = "role_id")
    @ApiModelProperty(value="角色id")
    private Long roleId;

    private static final long serialVersionUID = 1L;

    public static final String COL_ROLE_USER_ID = "role_user_id";

    public static final String COL_USER_ID = "user_id";

    public static final String COL_ROLE_ID = "role_id";
}