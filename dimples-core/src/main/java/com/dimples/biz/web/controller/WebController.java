package com.dimples.biz.web.controller;

import com.dimples.biz.monitor.service.LoginLogService;
import com.dimples.biz.monitor.vo.StatisticVO;
import com.dimples.biz.system.dto.UserDetailDTO;
import com.dimples.biz.system.po.User;
import com.dimples.biz.system.service.DeptService;
import com.dimples.biz.system.service.RoleService;
import com.dimples.biz.system.service.UserService;
import com.dimples.biz.web.constant.WebConstant;
import com.dimples.core.controller.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.NotBlank;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/1/21
 */
@Slf4j
@Validated
@Controller("webController")
@RequestMapping("web")
public class WebController extends BaseController {

    private LoginLogService loginLogService;
    private RoleService roleService;
    private DeptService deptService;
    private UserService userService;

    @Autowired
    public WebController(LoginLogService loginLogService, RoleService roleService, DeptService deptService, UserService userService) {
        this.userService = userService;
        this.loginLogService = loginLogService;
        this.roleService = roleService;
        this.deptService = deptService;
    }

    @GetMapping("login")
    public ModelAndView login() {
        return new ModelAndView(WebConstant.LOGIN);
    }

    @GetMapping("index/{username}")
    public ModelAndView index(@NotBlank(message = "{required}") @PathVariable String username) {
        log.info("============= 当前登陆用户：{} =============", username);
        // 先获取用户的登录信息
        UserDetailDTO userDetail = userService.getUserInfoByUsername(username);
        StatisticVO statistic = StatisticVO.builder()
                .loginTotal(loginLogService.count())
                .todayIpTotal(loginLogService.todayIpTotal())
                .todayTotal(loginLogService.todayTotal())
                .build();
        statistic.buildLastLoginTime(loginLogService.findByUsername(username));
        statistic.buildRole(roleService.findByUserId(userDetail.getUserId()));
        statistic.buildDept(deptService.findByUserId(userDetail.getUserId()));
        ModelAndView view = new ModelAndView(WebConstant.INDEX);
        view.addObject("user", userDetail);
        view.addObject("statistic", statistic);
        return view;
    }

    @GetMapping("user")
    public ModelAndView user() {
        User user = getCurrentUser();
        ModelAndView view = new ModelAndView(WebConstant.USER);
        view.addObject("user", user);
        return view;
    }

    @GetMapping(WebConstant.VIEW_PREFIX + "system/user/add")
    public ModelAndView systemUserAdd() {
        return new ModelAndView(WebConstant.USER_ADD);
    }

    @GetMapping(WebConstant.VIEW_PREFIX + "404")
    public ModelAndView error404() {
        return new ModelAndView(WebConstant.ERROR_404);
    }

    @GetMapping(WebConstant.VIEW_PREFIX + "403")
    public ModelAndView error403() {
        return new ModelAndView(WebConstant.ERROR_403);
    }

    @GetMapping(WebConstant.VIEW_PREFIX + "500")
    public ModelAndView error500() {
        return new ModelAndView(WebConstant.ERROR_500);
    }

}




