package com.dimples.core.transport;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @author MrBird
 */
@ApiModel(value = "分页排序参数")
@Data
@ToString
public class QueryRequest implements Serializable {

    private static final long serialVersionUID = -4869594085374385813L;
    /**
     * 当前页面数据量
     */
    @ApiModelProperty(value = "分页大小")
    private int pageSize = 10;
    /**
     * 当前页码
     */
    @ApiModelProperty(value = "页码")
    private int pageNum = 1;
    /**
     * 排序字段
     */
    @ApiModelProperty(value = "排序字段")
    private String field;
    /**
     * 排序规则，asc升序，desc降序
     */
    @ApiModelProperty(value = "升序/降序")
    private String order;
}
