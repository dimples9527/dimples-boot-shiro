package com.dimples.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dimples.system.po.RoleUser;

import java.util.List;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/1/24
 */
public interface RoleUserService extends IService<RoleUser> {

    /**
     * 通过角色 id 删除
     *
     * @param roleIds 角色 id
     */
    void deleteUserRolesByRoleId(List<String> roleIds);

    /**
     * 通过用户 id 删除
     *
     * @param userIds 用户 id
     */
    void deleteUserRolesByUserId(List<String> userIds);
}
