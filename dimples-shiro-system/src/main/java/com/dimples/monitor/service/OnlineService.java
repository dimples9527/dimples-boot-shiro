package com.dimples.monitor.service;

import com.dimples.monitor.po.OnlineUser;

import java.util.List;

/**
 * @author MrBird
 */
public interface OnlineService {

    /**
     * 获取在线用户列表
     *
     * @param username 用户名
     * @return List<ActiveUser>
     */
    List<OnlineUser> list(String username);

    /**
     * 踢出用户
     *
     * @param sessionId sessionId
     */
    void forceLogout(String sessionId);
}
