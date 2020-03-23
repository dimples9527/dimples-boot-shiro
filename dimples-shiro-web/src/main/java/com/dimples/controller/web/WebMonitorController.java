package com.dimples.controller.web;

import com.dimples.common.controller.BaseController;
import com.dimples.constant.WebConstant;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
@RequestMapping("web/monitor")
public class WebMonitorController extends BaseController {

    @GetMapping("online")
    @RequiresPermissions("online:view")
    public ModelAndView online() {
        return new ModelAndView(WebConstant.ONLINE);
    }

}




