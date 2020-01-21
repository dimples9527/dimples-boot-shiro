package com.dimples.biz.web.controller;

import com.dimples.biz.web.constant.PageConstant;
import com.dimples.core.annotation.OpsLog;
import com.dimples.core.eunm.OpsLogTypeEnum;
import com.dimples.core.properties.DimplesProperties;
import com.dimples.core.properties.ValidateCodeProperties;
import com.wf.captcha.utils.CaptchaUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/1/21
 */
@Controller("webLoginController")
public class LoginController {

    private DimplesProperties properties;

    @Autowired
    public LoginController(DimplesProperties properties) {
        this.properties = properties;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView(PageConstant.LOGIN);
    }

    @OpsLog(value = "获取图形验证码",type = OpsLogTypeEnum.CAPTCHA)
    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ValidateCodeProperties code = properties.getCode();
        // 设置位数
        CaptchaUtil.out(code.getLength(), request, response);
        // 设置宽、高、位数
        CaptchaUtil.out(code.getHeight(), code.getWidth(), code.getLength(), request, response);
        CaptchaUtil.out(request, response);
    }

}
