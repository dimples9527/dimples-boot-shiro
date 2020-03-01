package com.dimples.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dimples.system.mapper.RoleMapper;
import com.dimples.system.po.MenuRole;
import com.dimples.system.po.Role;
import com.dimples.system.service.MenuRoleService;
import com.dimples.system.service.RoleService;
import com.dimples.system.service.RoleUserService;
import com.dimples.core.constant.DimplesConstant;
import com.dimples.framework.shiro.ShiroRealm;
import com.dimples.core.transport.QueryRequest;
import com.dimples.core.util.SortUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/1/29
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    private MenuRoleService menuRoleService;
    private RoleUserService roleUserService;
    private ShiroRealm shiroRealm;

    @Autowired
    public RoleServiceImpl(MenuRoleService menuRoleService, RoleUserService roleUserService, ShiroRealm shiroRealm) {
        this.menuRoleService = menuRoleService;
        this.roleUserService = roleUserService;
        this.shiroRealm = shiroRealm;
    }

    @Override
    public List<Role> findUserRole(String username) {
        return this.baseMapper.findUserRole(username);
    }

    @Override
    public List<Role> findByUserId(Long userId) {
        return this.baseMapper.findByUserId(userId);
    }


    @Override
    public List<Role> findRoles(Role role) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(role.getRoleName())) {
            queryWrapper.lambda().like(Role::getRoleName, role.getRoleName());
        }
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public IPage<Role> findRoles(Role role, QueryRequest request) {
        Page<Role> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "create_date", DimplesConstant.ORDER_DESC, false);
        return this.baseMapper.findRolePage(page, role);
    }

    @Override
    public Role findByName(String roleName) {
        return this.baseMapper.selectOne(new QueryWrapper<Role>().lambda().eq(Role::getRoleName, roleName));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createRole(Role role) {
        role.setCreateDate(new Date());
        this.baseMapper.insert(role);
        this.saveRoleMenus(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(Role role) {
        role.setModifyDate(new Date());
        this.updateById(role);
        List<String> roleIdList = new ArrayList<>();
        roleIdList.add(String.valueOf(role.getRoleId()));
        this.menuRoleService.deleteRoleMenusByRoleId(roleIdList);
        saveRoleMenus(role);

        shiroRealm.clearCache();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRoles(String roleIds) {
        List<String> list = Arrays.asList(roleIds.split(StringPool.COMMA));
        this.baseMapper.delete(new QueryWrapper<Role>().lambda().in(Role::getRoleId, list));

        this.menuRoleService.deleteRoleMenusByRoleId(list);
        this.roleUserService.deleteUserRolesByRoleId(list);
    }

    private void saveRoleMenus(Role role) {
        if (StringUtils.isNotBlank(role.getMenuIds())) {
            String[] menuIds = role.getMenuIds().split(StringPool.COMMA);
            List<MenuRole> roleMenus = new ArrayList<>();
            Arrays.stream(menuIds).forEach(menuId -> {
                MenuRole roleMenu = new MenuRole();
                roleMenu.setMenuId(Long.valueOf(menuId));
                roleMenu.setRoleId(role.getRoleId());
                roleMenus.add(roleMenu);
            });
            menuRoleService.saveBatch(roleMenus);
        }
    }

}











