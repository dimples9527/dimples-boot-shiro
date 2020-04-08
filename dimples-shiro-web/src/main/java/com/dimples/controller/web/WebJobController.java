package com.dimples.controller.web;

import com.dimples.common.controller.BaseController;
import com.dimples.constant.WebConstant;
import com.dimples.framework.shiro.ShiroHelper;
import com.dimples.system.po.User;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("web/job")
public class WebJobController extends BaseController {

    private ShiroHelper shiroHelper;

    @Autowired
    public WebJobController(ShiroHelper shiroHelper) {
        this.shiroHelper = shiroHelper;
    }

    @GetMapping
    @RequiresPermissions("job:view")
    public ModelAndView job() {
        ModelAndView view = new ModelAndView(WebConstant.JOB);
        this.getUserShiroInfo(view);
        return view;
    }

    @GetMapping("add")
    @RequiresPermissions("job:add")
    public ModelAndView jobAdd() {
        ModelAndView view = new ModelAndView(WebConstant.JOB_ADD);
        this.getUserShiroInfo(view);
        return view;
    }

    private void getUserShiroInfo(ModelAndView view) {
        AuthorizationInfo authorizationInfo = shiroHelper.getCurrentuserAuthorizationInfo();
        User currentUser = getCurrentUser();
        view.addObject("shiroUser", currentUser);
        view.addObject("permissions", authorizationInfo.getStringPermissions());
        view.addObject("roles", authorizationInfo.getRoles());
    }

}




