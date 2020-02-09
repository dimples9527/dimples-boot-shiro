package com.dimples.biz.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dimples.biz.system.po.Role;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/1/29
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

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
     * 查找角色详情
     *
     * @param page 分页
     * @param role 角色
     * @return IPage<User>
     */
    IPage<Role> findRolePage(@Param("page") Page<Role> page, @Param("role") Role role);

}