package com.dimples.biz.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dimples.biz.system.mapper.MenuRoleMapper;
import com.dimples.biz.system.po.MenuRole;
import com.dimples.biz.system.service.MenuRoleService;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/2/9
 */
@Service
public class MenuRoleServiceImpl extends ServiceImpl<MenuRoleMapper, MenuRole> implements MenuRoleService {

    @Override
    public void deleteRoleMenusByRoleId(List<String> roleIds) {
        this.baseMapper.delete(new QueryWrapper<MenuRole>().lambda().in(MenuRole::getRoleId, roleIds));
    }

    @Override
    public void deleteRoleMenusByMenuId(List<String> menuIds) {
        this.baseMapper.delete(new QueryWrapper<MenuRole>().lambda().in(MenuRole::getMenuId, menuIds));
    }

}
