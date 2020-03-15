package com.dimples.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dimples.system.mapper.UserInfoMapper;
import com.dimples.system.po.UserInfo;
import com.dimples.system.service.UserInfoService;

import org.springframework.stereotype.Service;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/3/13
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    @Override
    public void updateByUserId(UserInfo userInfo) {
        this.baseMapper.updateByUserId(userInfo);
    }
}
