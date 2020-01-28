package com.dimples.biz.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dimples.biz.system.mapper.LoginLogMapper;
import com.dimples.biz.system.po.LoginLog;
import com.dimples.biz.system.service.LoginLogService;
import com.dimples.core.util.AddressUtil;
import com.dimples.core.util.HttpContextUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import javax.annotation.Resource;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/1/28
 */
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog> implements LoginLogService {

    @Resource
    private LoginLogMapper loginLogMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveLoginLog(String username) {
        String ip = HttpContextUtil.getIp();
        String address = AddressUtil.getAddress(ip, 1);
        LoginLog loginLog = LoginLog.builder()
                .username(username)
                .loginTime(new Date())
                .loginIp(ip)
                .loginLocation(address)
                .build();
        loginLog.setSystemBrowserInfo();
        this.save(loginLog);
    }
}

