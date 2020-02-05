package com.dimples.biz.system.controller;

import com.dimples.biz.system.po.Role;
import com.dimples.biz.system.service.RoleService;
import com.dimples.core.annotation.OpsLog;
import com.dimples.core.eunm.OpsLogTypeEnum;
import com.dimples.core.transport.ResponseDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/2/5
 */
@Slf4j
@Api(value = "角色管理模块", tags = "角色管理模块")
@RestController
@RequestMapping("role")
public class RoleController {

    private RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @ApiOperation(value = "获取角色信息", notes = "获取角色信息")
    @OpsLog(value = "获取角色信息", type = OpsLogTypeEnum.SELECT)
    @GetMapping
    public ResponseDTO getAllRoles(Role role) {
        return ResponseDTO.success(roleService.findRoles(role));
    }

}











