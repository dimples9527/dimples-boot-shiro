package com.dimples.biz.system.service;

import com.dimples.biz.common.dto.MenuTreeDTO;
import com.dimples.biz.system.po.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/2/9
 */
public interface MenuService extends IService<Menu> {

    /**
     * 查找用户权限集
     *
     * @param username 用户名
     * @return 用户权限集合
     */
    List<Menu> findUserPermissions(String username);

    /**
     * 查找用户菜单集合
     *
     * @param username 用户名
     * @return 用户菜单集合
     */
    MenuTreeDTO<Menu> findUserMenus(String username);

    /**
     * 查找所有的菜单/按钮 （树形结构）
     *
     * @param menu 菜单对象
     * @return MenuTree<Menu>
     */
    MenuTreeDTO<Menu> findMenus(Menu menu);

    /**
     * 查找所有的菜单/按钮
     *
     * @param menu 菜单对象
     * @return MenuTree<Menu>
     */
    List<Menu> findMenuList(Menu menu);

    /**
     * 新增菜单（按钮）
     *
     * @param menu 菜单（按钮）对象
     */
    void createMenu(Menu menu);

    /**
     * 删除菜单（按钮）
     *
     * @param menuIds 菜单（按钮）id
     */
    void deleteMeuns(String menuIds);

    /**
     * 修改菜单（按钮）
     *
     * @param menu 菜单（按钮）对象
     */
    void updateMenu(Menu menu);

}



