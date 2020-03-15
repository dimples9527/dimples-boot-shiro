package com.dimples.common.util;

import com.dimples.core.exception.BizException;
import com.dimples.system.po.User;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/3/13
 */
public class DimplesUtil {

    /**
     * 使用 PropertyUtils.copyProperties() 方法的原因是：
     * 在 User user = (User) getSubject().getPrincipal() 类型转换时同时在pom中添加了devtools热部署,
     * 会产生一个类型转换异常
     *
     * @return User
     */
    public static User getCurrentUser() {
        Object principal = getSubject().getPrincipal();
        User user = new User();
        try {
            PropertyUtils.copyProperties(user, principal);
        } catch (Exception e) {
            throw new BizException("Principal 转换为 User 装换异常", e);
        }
        return user;
    }

    public static boolean isCurrentUser(Long id) {
        User currentUser = DimplesUtil.getCurrentUser();
        return currentUser.getUserId().equals(id);
    }

    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

}
