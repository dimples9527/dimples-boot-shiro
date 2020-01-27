package com.dimples.core.framework.shiro;

import com.dimples.biz.system.po.User;
import com.dimples.biz.system.service.UserService;
import com.dimples.core.eunm.CodeAndMessageEnum;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/13
 */
@Component
public class ShiroRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;

    /**
     * 权限校验
     * 1. 获取当前登录用户信息
     * 2. 获取当前用户关联的角色、权限等
     * 3. 将角色、权限封装到AuthorizationInfo对象中并返回
     *
     * @param principals PrincipalCollection
     * @return AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        return null;
    }

    /**
     * 身份校验
     * 1. 从token中获取输入的用户名
     * 2. 通过username查询数据库得到用户对象
     * 3. 调用Authenticator进行密码校验
     *
     * @param token AuthenticationToken
     * @return AuthenticationInfo
     * @throws AuthenticationException AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());
        User user = userService.findByName(username);
        if (user == null) {
            throw new UnknownAccountException(CodeAndMessageEnum.USER_NOT_EXISTED.getMessage());
        }
        if (!StringUtils.equals(password, user.getPassword())) {
            throw new IncorrectCredentialsException(CodeAndMessageEnum.ACCOUNT_ERROR.getMessage());
        }
        if (StringUtils.equals(User.STATUS_LOCK, user.getStatus())) {
            throw new LockedAccountException(CodeAndMessageEnum.ACCOUNT_LOCK.getMessage());
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(password), getName());
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("username", username);
        return authenticationInfo;
    }
}













