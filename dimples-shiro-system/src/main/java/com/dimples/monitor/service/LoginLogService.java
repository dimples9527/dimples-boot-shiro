package com.dimples.monitor.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dimples.core.transport.QueryRequest;
import com.dimples.monitor.po.LoginLog;

import java.util.List;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/1/28
 */
public interface LoginLogService extends IService<LoginLog> {

    /**
     * 保存登陆日志
     *
     * @param username String
     */
    void saveLoginLog(String username);

    /**
     * 获取登录日志分页信息
     *
     * @param loginLog 传参
     * @param request  request
     * @return IPage<LoginLog>
     */
    IPage<LoginLog> findLoginLogs(LoginLog loginLog, QueryRequest request);

    /**
     * 保存登录日志
     *
     * @param loginLog 登录日志
     */
    void saveLoginLog(LoginLog loginLog);

    /**
     * 删除登录日志
     *
     * @param ids 日志 id集合
     */
    void deleteLoginLogs(String ids);

    /**
     * 统计当日访问IP量
     *
     * @return Integer
     */
    Integer todayIp();

    /**
     * 统计当日访问量
     *
     * @return Integer
     */
    Integer todayVisitCount();

    /**
     * 根据用户名查询到了日志信息
     * 根据日期倒序
     *
     * @param username 用户名
     * @return List<LoginLog>
     */
    List<LoginLog> findByUsername(String username);

}















