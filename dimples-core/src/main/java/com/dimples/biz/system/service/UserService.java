package com.dimples.biz.system.service;

import com.dimples.biz.system.po.User;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/14
 */
public interface UserService {
    /**
     * 根据用户名查询用户
     *
     * @param username String
     * @return User
     */
    User findByName(String username);

    /**
     * 增加一条数据
     *
     * @param user User
     */
    void insertSelective(User user);
}

