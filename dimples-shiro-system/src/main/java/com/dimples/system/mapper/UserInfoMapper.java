package com.dimples.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dimples.system.po.UserInfo;

import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/2/3
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    /**
     * 根据用户id更新用户信息
     *
     * @param userInfo UserInfo
     */
    void updateByUserId(UserInfo userInfo);
}