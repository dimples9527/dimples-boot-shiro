package com.dimples.monitor.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dimples.monitor.po.LoginLog;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
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
    List<String> todayIpTotal();

    /**
     * 统计当日访问量
     *
     * @return Integer
     */
    Integer todayTotal();

    /**
     * 根据用户名查询到了日志信息
     * 根据日期倒序
     *
     * @param username 用户名
     * @return List<LoginLog>
     */
    List<LoginLog> findByUsername(@Param("username") String username);
}