package com.dimples.system.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dimples.system.po.UserInfo;
import com.dimples.system.mapper.UserInfoMapper;
import com.dimples.system.service.UserInfoService;
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService{

}
