package com.dimples.biz.web.controller;

import com.dimples.biz.web.constant.WebConstant;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/1/21
 */
@Slf4j
@Controller
@RequestMapping("web/sys")
public class WebSysController {

    @GetMapping("login")
    public ModelAndView login() {
        return new ModelAndView(WebConstant.LOGIN);
    }

    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView(WebConstant.INDEX);
    }

    @GetMapping("user")
    public ModelAndView user() {
        return new ModelAndView(WebConstant.USER);
    }

    @GetMapping("user/add")
    public ModelAndView systemUserAdd() {
        return new ModelAndView(WebConstant.USER_ADD);
    }

    @GetMapping("404")
    public ModelAndView error404() {
        return new ModelAndView(WebConstant.ERROR_404);
    }

    @GetMapping("403")
    public ModelAndView error403() {
        return new ModelAndView(WebConstant.ERROR_403);
    }

    @GetMapping("500")
    public ModelAndView error500() {
        return new ModelAndView(WebConstant.ERROR_500);
    }

}




