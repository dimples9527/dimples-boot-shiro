package com.dimples.biz.system.controller;

import com.dimples.biz.system.po.User;
import com.dimples.biz.system.service.UserService;
import com.dimples.core.annotation.OpsLog;
import com.dimples.core.eunm.OpsLogTypeEnum;
import com.dimples.core.exception.BizException;
import com.dimples.core.transport.ResponseDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户管理
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/14
 */
@Slf4j
@Api(value = "用户管理模块", tags = "用户管理模块")
@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "增加用户", notes = "增加用户")
    @OpsLog(value = "增加用户", type = OpsLogTypeEnum.ADD)
    @PostMapping("/add")
    public ResponseDTO add(@ApiParam(name = "user", value = "用户User对象", required = true) User user) {
        try {
            userService.add(user);
        } catch (Exception e) {
            throw new BizException(e.getMessage());
        }
        return ResponseDTO.success();
    }


}
















