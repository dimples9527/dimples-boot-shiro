package com.dimples.controller.system;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dimples.common.annotation.OpsLog;
import com.dimples.core.eunm.OpsLogTypeEnum;
import com.dimples.core.transport.DimplesResponse;
import com.dimples.core.transport.QueryRequest;
import com.dimples.system.po.Role;
import com.dimples.system.service.RoleService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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
    public DimplesResponse getAllRoles(Role role) {
        return DimplesResponse.success(roleService.findRoles(role));
    }

    @ApiOperation(value = "获取角色信息", notes = "获取角色信息")
    @OpsLog(value = "获取角色信息", type = OpsLogTypeEnum.SELECT)
    @GetMapping("list")
    @RequiresPermissions("role:view")
    public DimplesResponse roleList(Role role, QueryRequest request) {
        IPage<Role> roles = this.roleService.findRoles(role, request);
        return DimplesResponse.success(roles);
    }

    @ApiOperation(value = "新增角色", notes = "新增角色")
    @OpsLog(value = "新增角色", type = OpsLogTypeEnum.ADD)
    @PostMapping
    @RequiresPermissions("role:add")
    public DimplesResponse addRole(@Valid Role role) {
        this.roleService.createRole(role);
        return DimplesResponse.success();
    }

    @ApiOperation(value = "删除角色", notes = "删除角色")
    @ApiImplicitParam(name = "roleIds", value = "角色字符串，以,分割", paramType = "string", dataType = "path", required = true)
    @OpsLog(value = "删除角色", type = OpsLogTypeEnum.DELETE)
    @PostMapping("delete/{roleIds}")
    @RequiresPermissions("role:delete")
    public DimplesResponse deleteRoles(@NotBlank(message = "roleIds 不能为空") @PathVariable String roleIds) {
        this.roleService.deleteRoles(roleIds);
        return DimplesResponse.success();
    }

    @ApiOperation(value = "修改角色", notes = "修改角色")
    @OpsLog(value = "修改角色", type = OpsLogTypeEnum.UPDATE)
    @PostMapping("update")
    @RequiresPermissions("role:update")
    public DimplesResponse updateRole(Role role) {
        this.roleService.updateRole(role);
        return DimplesResponse.success();
    }

    @ApiOperation(value = "导出角色Excel", notes = "导出角色Excel")
    @OpsLog(value = "导出角色Excel", type = OpsLogTypeEnum.EXPORT)
    @GetMapping("excel")
    @RequiresPermissions("role:export")
    public void export(HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easy-excel没有关系
        String fileName = URLEncoder.encode("测试", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        // 获取数据
        List<Role> list = this.roleService.list();
        EasyExcel.write(response.getOutputStream(), Role.class).sheet("角色信息").doWrite(list);
    }

}











