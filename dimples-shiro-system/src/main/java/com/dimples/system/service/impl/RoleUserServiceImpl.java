package com.dimples.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dimples.system.mapper.RoleUserMapper;
import com.dimples.system.po.RoleUser;
import com.dimples.system.service.RoleUserService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/1/24
 */
@Service
public class RoleUserServiceImpl extends ServiceImpl<RoleUserMapper, RoleUser> implements RoleUserService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUserRolesByRoleId(List<String> roleIds) {
        this.baseMapper.delete(new QueryWrapper<RoleUser>().lambda().in(RoleUser::getRoleId, roleIds));
    }

    @Override
    public void deleteUserRolesByUserId(List<String> userIds) {
        this.baseMapper.delete(new QueryWrapper<RoleUser>().lambda().in(RoleUser::getUserId, userIds));
    }
}
