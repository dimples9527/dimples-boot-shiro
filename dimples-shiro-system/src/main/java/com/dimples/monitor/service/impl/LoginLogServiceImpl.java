package com.dimples.monitor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dimples.core.constant.DimplesConstant;
import com.dimples.core.transport.QueryRequest;
import com.dimples.core.util.AddressUtil;
import com.dimples.core.util.HttpContextUtil;
import com.dimples.core.util.IPUtil;
import com.dimples.core.util.SortUtil;
import com.dimples.monitor.mapper.LoginLogMapper;
import com.dimples.monitor.po.LoginLog;
import com.dimples.monitor.service.LoginLogService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
        LoginLog loginLog = new LoginLog();
        loginLog.setUsername(username);
        loginLog.setLoginTime(new Date());
        loginLog.setLoginIp(ip);
        loginLog.setLoginLocation(address);
        loginLog.setSystemBrowserInfo();
        this.save(loginLog);
    }

    @Override
    public Integer todayIp() {
        List<String> ipTotal = loginLogMapper.todayIpTotal();
        return Math.max(ipTotal.size(), 0);
    }

    @Override
    public Integer todayVisitCount() {
        return loginLogMapper.todayTotal();
    }

    @Override
    public List<LoginLog> findByUsername(String username) {
        return loginLogMapper.findByUsername(username);
    }

    @Override
    public IPage<LoginLog> findLoginLogs(LoginLog loginLog, QueryRequest request) {
        QueryWrapper<LoginLog> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(loginLog.getUsername())) {
            queryWrapper.lambda().eq(LoginLog::getUsername, loginLog.getUsername().toLowerCase());
        }
        if (StringUtils.isNotBlank(loginLog.getLoginTimeFrom()) && StringUtils.isNotBlank(loginLog.getLoginTimeTo())) {
            queryWrapper.lambda()
                    .ge(LoginLog::getLoginTime, loginLog.getLoginTimeFrom())
                    .le(LoginLog::getLoginTime, loginLog.getLoginTimeTo());
        }

        Page<LoginLog> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "loginTime", DimplesConstant.ORDER_DESC, true);

        return this.page(page, queryWrapper);
    }

    @Override
    public void saveLoginLog(LoginLog loginLog) {
        loginLog.setLoginTime(new Date());
        HttpServletRequest request = HttpContextUtil.getRequest();
        String ip = IPUtil.getIpAddr(request);
        loginLog.setLoginIp(ip);
        loginLog.setLoginLocation(AddressUtil.getCityInfo(ip));
        this.save(loginLog);
    }

    @Override
    public void deleteLoginLogs(String ids) {
        String[] id = StringUtils.splitByWholeSeparator(ids, StringPool.PIPE);
        List<String> list = Arrays.asList(id);
        this.baseMapper.deleteBatchIds(list);
    }
}

