package com.dimples.system.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@ApiModel(value="com-dimples-biz-system-po-MenuRole")
@Data
@TableName(value = "db_dimples_shiro.tb_menu_role")
public class MenuRole implements Serializable {
    @TableField(value = "role_id")
    @ApiModelProperty(value="")
    private Long roleId;

    @TableField(value = "menu_id")
    @ApiModelProperty(value="")
    private Long menuId;

    private static final long serialVersionUID = 1L;
}