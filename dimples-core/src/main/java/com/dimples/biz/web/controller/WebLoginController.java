package com.dimples.biz.web.controller;

import com.dimples.biz.web.constant.PageConstant;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/1/21
 */
@Controller("webLoginController")
@RequestMapping("web")
public class WebLoginController {

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView(PageConstant.LOGIN);
    }

}
