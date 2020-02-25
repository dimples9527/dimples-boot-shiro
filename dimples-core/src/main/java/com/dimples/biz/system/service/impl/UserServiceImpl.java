package com.dimples.biz.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dimples.biz.system.dto.UserDetailDTO;
import com.dimples.biz.system.mapper.UserMapper;
import com.dimples.biz.system.po.RoleUser;
import com.dimples.biz.system.po.User;
import com.dimples.biz.system.po.UserDept;
import com.dimples.biz.system.po.UserInfo;
import com.dimples.biz.system.service.RoleUserService;
import com.dimples.biz.system.service.UserDeptService;
import com.dimples.biz.system.service.UserInfoService;
import com.dimples.biz.system.service.UserService;
import com.dimples.core.constant.DimplesConstant;
import com.dimples.core.eunm.CodeAndMessageEnum;
import com.dimples.core.exception.BizException;
import com.dimples.core.transport.QueryRequest;
import com.dimples.core.util.MD5Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
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
    private UserInfoService userInfoService;
    private UserDeptService userDeptService;

    @Autowired
    public UserServiceImpl(RoleUserService roleUserService, UserMapper userMapper, UserInfoService userInfoService, UserDeptService userDeptService) {
        this.roleUserService = roleUserService;
        this.userMapper = userMapper;
        this.userInfoService = userInfoService;
        this.userDeptService = userDeptService;
    }

    @Override
    public User findByName(String username) {
        List<User> users = userMapper.findByUsername(username);
        return users.size() > 0 ? users.get(0) : null;
    }

    @Override
    public UserDetailDTO findUserDetailByName(String username) {
        return this.userMapper.findUserDetailByName(username);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(UserDetailDTO userDetail) {
        User user = new User();
        UserInfo userInfo = new UserInfo();
        user.setUsername(userDetail.getUsername());
        user.setCreateDate(new Date());
        user.setStatus(userDetail.getStatus());
        user.setPassword(MD5Util.encrypt(user.getUsername(), User.DEFAULT_PASSWORD));
        // 保存用户登陆信息
        this.userMapper.insertSelective(user);

        userInfo.setUserId(user.getUserId());
        userInfo.setEmail(userDetail.getEmail());
        userInfo.setMobile(userDetail.getMobile());
        userInfo.setGender(userDetail.getGender());
        userInfo.setAvatar(UserDetailDTO.DEFAULT_AVATAR);
        userInfo.setDescription(userDetail.getDescription());
        // 保存用户详细信息
        this.userInfoService.save(userInfo);
        // 保存用户部门
        String deptId = userDetail.getDeptId();
        if (deptId == null) {
            throw new BizException("用户部门为空");
        }
        UserDept userDept = new UserDept();
        userDept.setUserId(user.getUserId());
        userDept.setDeptId(Long.valueOf(deptId));
        this.userDeptService.save(userDept);

        // 保存用户角色
        if (userDetail.getRoleId() == null) {
            throw new BizException("用户角色为空");
        }
        String[] roles = userDetail.getRoleId().split(StringPool.COMMA);
        List<RoleUser> userRoles = new ArrayList<>();
        Arrays.stream(roles).forEach(roleId -> {
            RoleUser userRole = new RoleUser();
            userRole.setUserId(user.getUserId());
            userRole.setRoleId(Long.valueOf(roleId));
            userRoles.add(userRole);
        });
        this.roleUserService.saveBatch(userRoles);
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

    @Override
    public IPage<UserDetailDTO> findUserDetailList(User user) {
        Page<UserDetailDTO> page = new Page<>(1, 1);
        IPage<UserDetailDTO> userDetailPage = this.baseMapper.findUserDetailPage(page, user);
        List<UserDetailDTO> records = userDetailPage.getRecords();
        if (records.isEmpty()) {
            throw new BizException(CodeAndMessageEnum.NO_THIS_USER);
        }
        return userDetailPage;
    }

    @Override
    public void updateLoginTime(User user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setLastLoginTime(new Date());
        this.userInfoService.update(userInfo, new LambdaQueryWrapper<UserInfo>().eq(UserInfo::getUserId, user.getUserId()));
    }

    @Override
    public int insert(User record) {
        return userMapper.insert(record);
    }

    @Override
    public int insertSelective(User record) {
        return userMapper.insertSelective(record);
    }
}
























