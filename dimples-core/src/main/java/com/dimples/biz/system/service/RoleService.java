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
     * 根据用户id查询角色信息
     *
     * @param userId 用户id
     * @return List<Role>
     */
    List<Role> findByUserId(Long userId);

}
