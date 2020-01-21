package com.dimples.biz.system.controller;

import com.dimples.biz.system.service.impl.ValidateCodeServiceImpl;
import com.dimples.core.annotation.OpsLog;
import com.dimples.core.eunm.OpsLogTypeEnum;
import com.dimples.core.exception.BizException;
import com.dimples.core.properties.DimplesProperties;
import com.dimples.core.transport.ResponseDTO;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户登陆
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/14
 */
@Slf4j
@Api(tags = "用户登录模块")
@RestController
@RequestMapping("sys")
public class LoginController {

    private DimplesProperties properties;
    private ValidateCodeServiceImpl validateCodeService;

    @Autowired
    public LoginController(DimplesProperties properties, ValidateCodeServiceImpl validateCodeService) {
        this.properties = properties;
        this.validateCodeService = validateCodeService;
    }


    @ApiOperation(value = "用户登陆", notes = "用户登陆")
    @OpsLog(value = "用户登陆", type = OpsLogTypeEnum.LOGIN)
    @PostMapping("/login")
    public ResponseDTO login(@ApiParam(name = "username", value = "用户名", required = true) String username,
                             @ApiParam(name = "password", value = "密码", required = true) String password,
                             @RequestParam(defaultValue = "false") Boolean remember) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, remember);
        try {
            getSubject().login(token);
        } catch (Exception e) {
            throw new BizException(e.getMessage());
        }
        log.info("是否登录==>{}", getSubject().isAuthenticated());
        return ResponseDTO.success();
    }

    private static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    @ApiOperation(value = "退出登录", notes = "退出登录")
    @OpsLog(value = "退出登录", type = OpsLogTypeEnum.LOGOUT)
    @GetMapping("/logout")
    public ResponseDTO logout() {
        getSubject().logout();
        return ResponseDTO.success();
    }

    @OpsLog(value = "获取图形验证码",type = OpsLogTypeEnum.CAPTCHA)
    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /*ValidateCodeProperties code = properties.getCode();
        // 设置位数
        CaptchaUtil.out(code.getLength(), request, response);
        // 设置宽、高、位数
        CaptchaUtil.out(code.getHeight(), code.getWidth(), code.getLength(), request, response);
        CaptchaUtil.out(request, response);*/
        validateCodeService.create(request, response);
    }

}















