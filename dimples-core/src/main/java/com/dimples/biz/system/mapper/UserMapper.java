package com.dimples.biz.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dimples.biz.system.po.User;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/1/30
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return List<User>
     */
    List<User> findByUsername(@Param("username") String username);

}