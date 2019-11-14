package com.dimples.biz.system.service.impl;

import com.dimples.biz.system.po.User;
import com.dimples.biz.system.service.UserService;
import com.dimples.core.util.PasswordHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/14
 */
@Service
public class UserServiceImpl implements UserService {

    private PasswordHelper passwordHelper;

    @Autowired
    public UserServiceImpl(PasswordHelper passwordHelper) {
        this.passwordHelper = passwordHelper;
    }

    @Override
    public User findByName(String username) {
        return null;
    }

    @Override
    public void insertSelective(User user) {
        user.setCreateTime(new Date());
        passwordHelper.encryptPassword(user);
        //然后直接调用存储方法
    }
}













