package com.dimples.biz.system.service;

import com.dimples.biz.system.po.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/1/29
 */
public interface RoleService extends IService<Role> {

    /**
     * 通过用户名查找用户角色
     *
     * @param username 用户名
     * @return 用户角色集合
     */
    List<Role> findUserRole(String username);

    /**
     * 根据用户id查询角色信息
     *
     * @param userId 用户id
     * @return List<Role>
     */
    List<Role> findByUserId(Long userId);

    /**
     * 查找所有角色
     *
     * @param role 角色对象（用于传递查询条件）
     * @return 角色集合
     */
    List<Role> findRoles(Role role);
}
