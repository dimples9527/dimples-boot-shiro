package com.dimples.monitor.service;

import com.dimples.monitor.po.ActiveUser;

import java.util.List;

/**
 * @author MrBird
 */
public interface SessionService {

    /**
     * 获取在线用户列表
     *
     * @param username 用户名
     * @return List<ActiveUser>
     */
    List<ActiveUser> list(String username);

    /**
     * 踢出用户
     *
     * @param sessionId sessionId
     */
    void forceLogout(String sessionId);
}
