package com.dimples.core.framework.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义验证
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/19
 */
@Configuration
public class ShiroHashedCredentialsMatcher extends HashedCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        boolean matches = false;
        ShiroToken shiroToken = (ShiroToken) token;
        switch (shiroToken.getLoginType()) {
            case ShiroToken.LOGIN_PASS:
                matches = super.doCredentialsMatch(token, info);
                break;
            case ShiroToken.LOGIN_NO_PASS:
                matches = true;
                break;
            default:
        }
        return matches;
    }
}
















