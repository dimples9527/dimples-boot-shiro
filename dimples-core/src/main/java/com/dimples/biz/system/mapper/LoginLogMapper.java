package com.dimples.biz.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dimples.biz.system.po.LoginLog;
import org.apache.ibatis.annotations.Mapper;

/**
  *
  * @author zhongyj <1126834403@qq.com><br/>
  * @date 2020/1/28
  */
@Mapper
public interface LoginLogMapper extends BaseMapper<LoginLog> {

    /**
     * 统计当日访问IP量
     *
     * @return Integer
     */
    Integer todayIpTotal();

    /**
     * 统计当日访问量
     *
     * @return Integer
     */
    Integer todayTotal();
}