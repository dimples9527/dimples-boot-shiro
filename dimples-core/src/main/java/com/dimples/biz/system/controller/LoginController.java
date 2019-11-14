package com.dimples.biz.system.controller;

import com.dimples.core.annotation.OpsLog;
import com.dimples.core.eunm.OpsLogTypeEnum;
import com.dimples.core.exception.BizException;
import com.dimples.core.result.ResultCommon;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户登陆
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/14
 */
@Slf4j
@Api(value = "用户登录模块", tags = "用户登录模块")
@RestController
public class LoginController {

    @ApiOperation(value = "用户登陆", notes = "用户登陆")
    @OpsLog(value = "用户登陆", type = OpsLogTypeEnum.LOGIN)
    @PostMapping("/login")
    public ResultCommon login(String username, String password, @RequestParam(defaultValue = "false") Boolean remember) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, remember);
        try {
            getSubject().login(token);
        } catch (Exception e) {
            throw new BizException(e.getMessage());
        }
        log.info("是否登录==>{}", getSubject().isAuthenticated());
        return ResultCommon.success();
    }

    private static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    @ApiOperation(value = "退出登录", notes = "退出登录")
    @OpsLog(value = "退出登录", type = OpsLogTypeEnum.LOGOUT)
    @GetMapping("/logout")
    public ResultCommon logout() {
        getSubject().logout();
        return ResultCommon.success();
    }


}















