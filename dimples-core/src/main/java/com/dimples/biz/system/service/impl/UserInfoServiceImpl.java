package com.dimples.biz.system.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dimples.biz.system.po.UserInfo;
import com.dimples.biz.system.mapper.UserInfoMapper;
import com.dimples.biz.system.service.UserInfoService;
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService{

}
