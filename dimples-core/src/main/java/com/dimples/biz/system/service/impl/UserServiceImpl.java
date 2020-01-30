package com.dimples.biz.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dimples.biz.system.mapper.UserMapper;
import com.dimples.biz.system.po.RoleUser;
import com.dimples.biz.system.po.User;
import com.dimples.biz.system.service.RoleUserService;
import com.dimples.biz.system.service.UserService;
import com.dimples.core.constant.DimplesConstant;
import com.dimples.core.util.MD5Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private RoleUserService roleUserService;
    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(RoleUserService roleUserService, UserMapper userMapper) {
        this.roleUserService = roleUserService;
        this.userMapper = userMapper;
    }

    @Override
    public User findByName(String username) {
        List<User> users = userMapper.findByUsername(username);
        return users.size() > 0 ? users.get(0) : null;
    }

    @Override
    public void add(User user) {
        user.setCreateDate(new Date());
        user.setPassword(MD5Util.encrypt(user.getUsername(), user.getPassword()));
        //然后直接调用存储方法
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void register(String username, String password) {
        User user = User.builder()
                .password(MD5Util.encrypt(username, password))
                .username(username)
                .createDate(new Date())
                .status(User.STATUS_VALID)
                .build();
        // 保存用户信息
        this.save(user);
        RoleUser roleUser = new RoleUser();
        roleUser.setUserId(user.getUserId());
        roleUser.setRoleId(DimplesConstant.REGISTER_ROLE_ID);
        // 保存用户角色信息
        this.roleUserService.save(roleUser);
    }
}




















