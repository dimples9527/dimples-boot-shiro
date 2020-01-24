package com.dimples.biz.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dimples.biz.system.mapper.UserMapper;
import com.dimples.biz.system.po.User;
import com.dimples.biz.system.service.UserService;
import com.dimples.core.util.MD5Util;

import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User findByName(String username) {
        return null;
    }

    @Override
    public void add(User user) {
        user.setCreateDate(new Date());
        user.setPassword(MD5Util.encrypt(user.getUsername(), user.getPassword()));
        //然后直接调用存储方法
    }
}
















