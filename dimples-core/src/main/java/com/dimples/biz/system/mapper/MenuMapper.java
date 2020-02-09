package com.dimples.biz.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dimples.biz.system.po.Menu;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/2/9
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

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
    List<Menu> findUserMenus(String username);
}