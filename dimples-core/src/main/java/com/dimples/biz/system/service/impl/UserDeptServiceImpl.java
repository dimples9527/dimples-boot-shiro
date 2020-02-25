package com.dimples.biz.system.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dimples.biz.system.po.UserDept;
import com.dimples.biz.system.mapper.UserDeptMapper;
import com.dimples.biz.system.service.UserDeptService;
@Service
public class UserDeptServiceImpl extends ServiceImpl<UserDeptMapper, UserDept> implements UserDeptService{

}
