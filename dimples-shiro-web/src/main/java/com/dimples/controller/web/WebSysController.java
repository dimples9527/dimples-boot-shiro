package com.dimples.controller.web;

import com.dimples.system.dto.UserDetailDTO;
import com.dimples.system.po.User;
import com.dimples.system.service.UserService;
import com.dimples.constant.WebConstant;
import com.dimples.common.controller.BaseController;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
public class WebSysController extends BaseController {

    private UserService userService;

    @Autowired
    public WebSysController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("login")
    public ModelAndView login() {
        return new ModelAndView(WebConstant.LOGIN);
    }

    @GetMapping("index")
    public ModelAndView index() {
        ModelAndView view = new ModelAndView(WebConstant.INDEX);
        User user = getCurrentUser();
        UserDetailDTO userDetail = userService.findUserDetailList(user).getRecords().get(0);
        view.addObject("user", userDetail);
        return view;
    }

    @GetMapping("user")
    public ModelAndView user() {
        return new ModelAndView(WebConstant.USER);
    }

    @GetMapping("user/add")
    public ModelAndView systemUserAdd() {
        return new ModelAndView(WebConstant.USER_ADD);
    }

    @GetMapping("user/detail/{username}")
    @RequiresPermissions("user:view")
    public ModelAndView systemUserDetail(@PathVariable String username) {
        ModelAndView view = new ModelAndView(WebConstant.USER_DETAIL);
        buildUserModel(username, view, true);
        return view;
    }

    @GetMapping("user/update/{username}")
    @RequiresPermissions("user:update")
    public ModelAndView systemUserUpdate(@PathVariable String username) {
        ModelAndView view = new ModelAndView(WebConstant.USER_UPDATE);
        buildUserModel(username, view, false);
        return view;
    }

    @GetMapping("role")
    public ModelAndView role() {
        return new ModelAndView(WebConstant.ROLE);
    }

    @GetMapping("dept")
    public ModelAndView dept() {
        return new ModelAndView(WebConstant.DEPT);
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

    private void buildUserModel(@PathVariable String username, ModelAndView view, Boolean transform) {
        UserDetailDTO detail = userService.findUserDetailByName(username);
        if (transform) {
            String gender = detail.getGender();
            if (User.SEX_MALE.equals(gender)) {
                detail.setGender("男");
            } else if (User.SEX_FEMALE.equals(gender)) {
                detail.setGender("女");
            } else {
                detail.setGender("保密");
            }
        }
        view.addObject("user", detail);
    }

}




