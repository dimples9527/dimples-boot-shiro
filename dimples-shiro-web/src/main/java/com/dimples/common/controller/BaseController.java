package com.dimples.common.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dimples.core.exception.BizException;
import com.dimples.system.po.User;
import com.google.common.collect.Maps;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import java.util.Map;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/2/2
 */
public class BaseController {

    protected Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 使用 PropertyUtils.copyProperties() 方法的原因是：
     * 在 User user = (User) getSubject().getPrincipal() 类型转换时同时在pom中添加了devtools热部署,
     * 会产生一个类型转换异常
     *
     * @return User
     */
    protected User getCurrentUser() {
        Object principal = getSubject().getPrincipal();
        User user = new User();
        try {
            PropertyUtils.copyProperties(user, principal);
        } catch (Exception e) {
            throw new BizException("Principal 转换为 User 装换异常", e);
        }
        return user;
    }

    protected Session getSession() {
        return getSubject().getSession();
    }

    protected Session getSession(Boolean flag) {
        return getSubject().getSession(flag);
    }

    protected Map<String, Object> getDataTable(IPage<?> pageInfo) {
        Map<String, Object> data = Maps.newHashMap();
        data.put("records", pageInfo.getRecords());
        data.put("total", pageInfo.getTotal());
        return data;
    }

}
