package com.dimples.core.core.framework.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/13
 */
public class ShiroRealm extends AuthorizingRealm {

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
        /*String username = (String) token.getPrincipal();
        User user = userService.findByName(username);
        if (user == null) {
            throw new UnknownAccountException(BizExceptionEnum.USER_NOT_EXISTED.getMessage());
        }
        return new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()),
                getName()
        );*/
        return null;
    }
}













