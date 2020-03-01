package com.dimples.framework.shiro;

import com.dimples.system.po.Menu;
import com.dimples.system.po.Role;
import com.dimples.system.po.User;
import com.dimples.system.service.MenuService;
import com.dimples.system.service.RoleService;
import com.dimples.system.service.UserService;
import com.dimples.core.eunm.CodeAndMessageEnum;
import com.dimples.core.exception.BizException;

import org.apache.commons.beanutils.PropertyUtils;
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

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Resource;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/13
 */
@Component
public class ShiroRealm extends AuthorizingRealm {

    @Resource
    private UserService userService;
    @Resource
    private MenuService menuService;
    @Resource
    private RoleService roleService;

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
        // 由于shiro和热部署插件同时存在时，直接获取对象强制转换会报错，所以此处使用拷贝的方式转换
        Object principal = SecurityUtils.getSubject().getPrincipal();
        User user = new User();
        try {
            PropertyUtils.copyProperties(user, principal);
        } catch (Exception e) {
            throw new BizException("Principal 转换为 User 装换异常", e);
        }

        String userName = user.getUsername();

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        // 获取用户角色集
        List<Role> roleList = this.roleService.findUserRole(userName);
        Set<String> roleSet = roleList.stream().map(Role::getRoleName).collect(Collectors.toSet());
        simpleAuthorizationInfo.setRoles(roleSet);

        // 获取用户权限集
        List<Menu> permissionList = this.menuService.findUserPermissions(userName);
        Set<String> permissionSet = permissionList.stream().map(Menu::getPerms).collect(Collectors.toSet());
        simpleAuthorizationInfo.setStringPermissions(permissionSet);
        return simpleAuthorizationInfo;
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

    /**
     * 清除当前用户权限缓存
     * 使用方法：在需要清除用户权限的地方注入 ShiroRealm,
     * 然后调用其 clearCache方法。
     */
    public void clearCache() {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }

}













