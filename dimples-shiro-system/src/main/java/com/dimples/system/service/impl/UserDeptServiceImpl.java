package com.dimples.system.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dimples.system.po.UserDept;
import com.dimples.system.mapper.UserDeptMapper;
import com.dimples.system.service.UserDeptService;
@Service
public class UserDeptServiceImpl extends ServiceImpl<UserDeptMapper, UserDept> implements UserDeptService{

}
