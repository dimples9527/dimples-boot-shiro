package com.dimples.biz.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dimples.biz.system.dto.UserDetailDTO;
import com.dimples.biz.system.mapper.UserMapper;
import com.dimples.biz.system.po.RoleUser;
import com.dimples.biz.system.po.User;
import com.dimples.biz.system.service.RoleUserService;
import com.dimples.biz.system.service.UserService;
import com.dimples.core.constant.DimplesConstant;
import com.dimples.core.transport.QueryRequest;
import com.dimples.core.util.MD5Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/14
 */
@Slf4j
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
        User user = new User();
        user.setPassword(MD5Util.encrypt(username, password));
        user.setUsername(username);
        user.setStatus(User.STATUS_VALID);
        user.setCreateDate(new Date());
        // 保存用户信息
        this.save(user);
        RoleUser roleUser = new RoleUser();
        roleUser.setUserId(user.getUserId());
        roleUser.setRoleId(DimplesConstant.REGISTER_ROLE_ID);
        // 保存用户角色信息
        this.roleUserService.save(roleUser);
    }

    @Override
    public IPage<UserDetailDTO> findUserDetailList(User user, QueryRequest request) {
        Page<UserDetailDTO> page = new Page<>(request.getPageNum(), request.getPageSize());
        return this.baseMapper.findUserDetailPage(page, user);
    }
}




















