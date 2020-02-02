package com.dimples.biz.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dimples.biz.system.po.User;
import com.dimples.core.transport.QueryRequest;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/14
 */
public interface UserService extends IService<User> {
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
    void add(User user);

    /**
     * 注册用户
     *
     * @param username 用户名
     * @param password 密码
     */
    void register(String username, String password);

    /**
     * 查找用户详细信息
     *
     * @param request request
     * @param user    用户对象，用于传递查询条件
     * @return IPage
     */
    IPage<User> findUserDetailList(User user, QueryRequest request);
}








