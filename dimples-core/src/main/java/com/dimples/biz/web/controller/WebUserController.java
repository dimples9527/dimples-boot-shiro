package com.dimples.biz.web.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dimples.biz.system.po.User;
import com.dimples.biz.system.service.UserService;
import com.dimples.biz.web.constant.PageConstant;
import com.dimples.core.transport.QueryRequest;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/1/26
 */
@Slf4j
@Controller
@RequestMapping("web")
public class WebUserController {

    private UserService userService;

    @Autowired
    public WebUserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("user")
    public ModelAndView index() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        // TODO: 2020/1/29  开发时设置，生产环境去除
        user = new User();
        user.setUserId(4L);
        user.setUsername("zhongyj");
        IPage<User> userList = userService.findUserDetailList(new User(), new QueryRequest());
        ModelAndView view = new ModelAndView(PageConstant.USER);
        view.addObject("user", user);
        view.addObject("userList", userList);
        return view;
    }
}















