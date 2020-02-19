package com.dimples.biz.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dimples.biz.common.dto.MenuTreeDTO;
import com.dimples.biz.common.util.TreeUtil;
import com.dimples.biz.system.mapper.MenuMapper;
import com.dimples.biz.system.po.Menu;
import com.dimples.biz.system.service.MenuRoleService;
import com.dimples.biz.system.service.MenuService;
import com.dimples.core.framework.shiro.ShiroRealm;

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
 * @date 2020/2/9
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    private MenuRoleService menuRoleService;
    private ShiroRealm shiroRealm;

    @Autowired
    public MenuServiceImpl(MenuRoleService menuRoleService, ShiroRealm shiroRealm) {
        this.menuRoleService = menuRoleService;
        this.shiroRealm = shiroRealm;
    }

    @Override
    public List<Menu> findUserPermissions(String username) {
        return this.baseMapper.findUserPermissions(username);
    }

    @Override
    public MenuTreeDTO<Menu> findUserMenus(String username) {
        List<Menu> menus = this.baseMapper.findUserMenus(username);
        List<MenuTreeDTO<Menu>> trees = TreeUtil.convertMenus(menus);
        return TreeUtil.buildMenuTree(trees);
    }

    @Override
    public MenuTreeDTO<Menu> findMenus(Menu menu) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(menu.getMenuName())) {
            queryWrapper.lambda().like(Menu::getMenuName, menu.getMenuName());
        }
        queryWrapper.lambda().orderByAsc(Menu::getOrderNum);
        List<Menu> menus = this.baseMapper.selectList(queryWrapper);
        List<MenuTreeDTO<Menu>> trees = TreeUtil.convertMenus(menus);

        return TreeUtil.buildMenuTree(trees);
    }

    @Override
    public List<Menu> findMenuList(Menu menu) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(menu.getMenuName())) {
            queryWrapper.lambda().like(Menu::getMenuName, menu.getMenuName());
        }
        queryWrapper.lambda().orderByAsc(Menu::getMenuId).orderByAsc(Menu::getOrderNum);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public void createMenu(Menu menu) {
        menu.setCreateTime(new Date());
        // 处理菜单存储个别字段
        this.setMenu(menu);
        this.baseMapper.insert(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMeuns(String menuIds) {
        String[] menuIdsArray = menuIds.split(StringPool.COMMA);
        // 需要递归删除，同时需要解除与角色的绑定关系
        this.delete(Arrays.asList(menuIdsArray));

        shiroRealm.clearCache();
    }

    @Override
    public void updateMenu(Menu menu) {
        menu.setModifyTime(new Date());
        this.setMenu(menu);
        this.baseMapper.updateById(menu);
        shiroRealm.clearCache();
    }

    /**
     * 设置菜单字段信息
     *
     * @param menu Menu
     */
    private void setMenu(Menu menu) {
        if (menu.getParentId() == null) {
            menu.setParentId(Menu.TOP_NODE);
        }
        if (Menu.TYPE_BUTTON.equals(menu.getType())) {
            menu.setHref(null);
            menu.setIcon(null);
        }
    }

    /**
     * 递归删除菜单
     * 同时解除与角色的绑定关系
     *
     * @param menuIds 菜单集合
     */
    private void delete(List<String> menuIds) {
        List<String> list = new ArrayList<>(menuIds);
        removeByIds(menuIds);

        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Menu::getParentId, menuIds);
        List<Menu> menus = baseMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(menus)) {
            List<String> menuIdList = new ArrayList<>();
            menus.forEach(m -> menuIdList.add(String.valueOf(m.getMenuId())));
            list.addAll(menuIdList);
            this.menuRoleService.deleteRoleMenusByMenuId(list);
            this.delete(menuIdList);
        } else {
            this.menuRoleService.deleteRoleMenusByMenuId(list);
        }
    }

}



