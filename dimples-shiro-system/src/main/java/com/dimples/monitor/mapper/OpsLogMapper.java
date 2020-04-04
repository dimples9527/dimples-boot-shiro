package com.dimples.monitor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dimples.monitor.po.OpsLog;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/3/31
 */
@Mapper
public interface OpsLogMapper extends BaseMapper<OpsLog> {
}