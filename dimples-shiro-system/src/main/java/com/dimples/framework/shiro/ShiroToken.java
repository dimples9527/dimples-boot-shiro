package com.dimples.framework.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

import lombok.Getter;
import lombok.Setter;

/**
 * 自定义token
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/19
 */

public class ShiroToken extends UsernamePasswordToken {

    private static final long serialVersionUID = -2564928913725078138L;

    public static final String LOGIN_PASS = "password";
    public static final String LOGIN_NO_PASS = "no_password";

    @Setter
    @Getter
    private String loginType;

    public ShiroToken() {
        super();
    }


    public ShiroToken(String username, String password, String loginType, boolean rememberMe, String host) {
        super(username, password, rememberMe,  host);
        this.loginType = loginType;
    }

    /**免密登录*/
    public ShiroToken(String username) {
        super(username, "", false, null);
        this.loginType = ShiroToken.LOGIN_NO_PASS;
    }
    /**账号密码登录*/
    public ShiroToken(String username, String pwd) {
        super(username, pwd, false, null);
        this.loginType = ShiroToken.LOGIN_PASS;
    }

}






















