package com.dimples.biz.web.controller;

import com.dimples.biz.web.constant.PageConstant;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/1/26
 */
@Controller
@RequestMapping("web")
public class WebIndexController {

    @GetMapping("index")
    public ModelAndView index() {
        return new ModelAndView(PageConstant.INDEX);
    }
}















