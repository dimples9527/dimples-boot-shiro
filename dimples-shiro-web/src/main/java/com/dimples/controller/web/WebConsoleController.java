package com.dimples.controller.web;

import com.dimples.constant.WebConstant;

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
@RequestMapping("web/console")
public class WebConsoleController {

    @GetMapping()
    public ModelAndView console() {
        return new ModelAndView(WebConstant.CONSOLE);
    }

}




