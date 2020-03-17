package com.dimples.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dimples.common.util.DimplesUtil;
import com.dimples.common.util.MD5Util;
import com.dimples.core.constant.DimplesConstant;
import com.dimples.core.eunm.CodeAndMessageEnum;
import com.dimples.core.exception.BizException;
import com.dimples.core.exception.DataException;
import com.dimples.core.transport.QueryRequest;
import com.dimples.core.util.SortUtil;
import com.dimples.framework.shiro.ShiroRealm;
import com.dimples.system.dto.UserDetailDTO;
import com.dimples.system.mapper.UserMapper;
import com.dimples.system.po.RoleUser;
import com.dimples.system.po.User;
import com.dimples.system.po.UserDept;
import com.dimples.system.po.UserInfo;
import com.dimples.system.service.RoleUserService;
import com.dimples.system.service.UserDeptService;
import com.dimples.system.service.UserInfoService;
import com.dimples.system.service.UserService;

import org.apache.commons.lang3.StringUtils;
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

    private ShiroRealm shiroRealm;
    private RoleUserService roleUserService;
    private UserInfoService userInfoService;
    private UserDeptService userDeptService;

    @Autowired
    public UserServiceImpl(ShiroRealm shiroRealm, RoleUserService roleUserService, UserInfoService userInfoService, UserDeptService userDeptService) {
        this.shiroRealm = shiroRealm;
        this.roleUserService = roleUserService;
        this.userInfoService = userInfoService;
        this.userDeptService = userDeptService;
    }

    @Override
    public User findByName(String username) {
        List<User> users = this.baseMapper.findByUsername(username);
        return users.size() > 0 ? users.get(0) : null;
    }

    @Override
    public UserDetailDTO findUserDetailByName(String username) {
        return this.baseMapper.findUserDetailByName(username);
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
        this.baseMapper.insertSelective(user);

        userInfo.setUserId(user.getUserId());
        userInfo.setEmail(userDetail.getEmail());
        userInfo.setMobile(userDetail.getMobile());
        userInfo.setGender(userDetail.getGender());
        userInfo.setAvatar(UserDetailDTO.DEFAULT_AVATAR);
        userInfo.setDescription(userDetail.getDescription());
        // 保存用户详细信息
        this.userInfoService.save(userInfo);
        // 保存用户部门
        saveUserDept(userDetail, user);

        // 保存用户角色
        saveUserRole(userDetail, user);
    }

    private void saveUserRole(UserDetailDTO userDetail, User user) {
        if (userDetail.getRoleId() == null) {
            throw new BizException("用户角色为空");
        }
        String[] roles = userDetail.getRoleId().split(StringPool.COMMA);
        saveBatchUserRole(user, roles);
    }

    private void saveUserDept(UserDetailDTO userDetail, User user) {
        String deptId = userDetail.getDeptId();
        if (deptId == null) {
            throw new BizException("用户部门为空");
        }
        UserDept userDept = new UserDept();
        userDept.setUserId(user.getUserId());
        userDept.setDeptId(Long.valueOf(deptId));
        this.userDeptService.save(userDept);
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
        SortUtil.handlePageSort(request, page, "user_id", DimplesConstant.ORDER_ASC, false);
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
    public boolean updateById(User entity) {
        return super.updateById(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUser(UserDetailDTO userDetailDTO) {
        if (userDetailDTO.getUserId() == null) {
            throw new DataException("用户ID为空");
        }
        User user = UserDetailDTO.convertUser(userDetailDTO);
        // 更新用户
        user.setUsername(null);
        user.setCreateDate(null);
        user.setModifyDate(new Date());
        this.updateById(user);
        this.update(user, new UpdateWrapper<User>().eq(User.USER_ID, user.getUserId()));
        UserInfo userInfo = UserDetailDTO.convertUserInfo(userDetailDTO);
        this.userInfoService.updateByUserId(userInfo);
        // 更新关联角色
        this.roleUserService.remove(new LambdaQueryWrapper<RoleUser>().eq(RoleUser::getUserId, user.getUserId()));
        String[] roles = userDetailDTO.getRoleId().split(StringPool.COMMA);
        saveBatchUserRole(user, roles);
        // 更新部门信息
        this.userDeptService.remove(new LambdaQueryWrapper<UserDept>().eq(UserDept::getUserId, user.getUserId()));
        UserDept userDept = new UserDept();
        userDept.setUserId(user.getUserId());
        userDept.setDeptId(Long.valueOf(userDetailDTO.getDeptId()));
        this.userDeptService.save(userDept);
        // 清楚用户的session信息
        User currentUser = DimplesUtil.getCurrentUser();
        if (StringUtils.equalsIgnoreCase(currentUser.getUsername(), userDetailDTO.getUsername())) {
            shiroRealm.clearCache();
        }
    }

    private void saveBatchUserRole(User user, String[] roles) {
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
    public void deleteUsers(String userIds) {
        String[] ids = StringUtils.splitByWholeSeparator(userIds, StringPool.PIPE);
        List<String> list = Arrays.asList(ids);
        // 删除用户
        this.removeByIds(list);
        list.forEach(item -> {
            // 删除关联用户信息
            this.userInfoService.remove(new LambdaUpdateWrapper<UserInfo>().eq(UserInfo::getUserId, item));
            // 删除部门关联
            this.userDeptService.remove(new LambdaUpdateWrapper<UserDept>().eq(UserDept::getUserId, item));
        });
        // 删除关联角色
        this.roleUserService.deleteUserRolesByUserId(list);
    }
}
























