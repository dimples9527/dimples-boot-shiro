package com.dimples.biz.system.controller;


import com.dimples.biz.common.dto.MenuTreeDTO;
import com.dimples.biz.system.po.Menu;
import com.dimples.biz.system.po.User;
import com.dimples.biz.system.service.MenuService;
import com.dimples.core.annotation.OpsLog;
import com.dimples.core.controller.BaseController;
import com.dimples.core.eunm.OpsLogTypeEnum;
import com.dimples.core.exception.BizException;
import com.dimples.core.transport.DimplesResponse;
import com.wuwenze.poi.ExcelKit;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author MrBird
 */
@Slf4j
@Api(value = "菜单管理模块", tags = "菜单管理模块")
@RestController
@RequestMapping("menu")
public class MenuController extends BaseController {

    private MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @ApiOperation(value = "获取用户菜单", notes = "获取用户菜单")
    @ApiImplicitParam(name = "username", value = "用户名", paramType = "string", dataType = "path", required = true)
    @OpsLog(value = "获取用户菜单", type = OpsLogTypeEnum.SELECT)
    @GetMapping("{username}")
    public DimplesResponse getUserMenus(@NotBlank(message = "{required}") @PathVariable String username) {
        User currentUser = getCurrentUser();
        if (!StringUtils.equalsIgnoreCase(username, currentUser.getUsername())) {
            throw new BizException("您无权获取别人的菜单");
        }
        MenuTreeDTO<Menu> userMenus = this.menuService.findUserMenus(username);
        return DimplesResponse.success(userMenus);
    }

    @ApiOperation(value = "获取菜单树", notes = "获取菜单树")
    @OpsLog(value = "获取菜单树", type = OpsLogTypeEnum.SELECT)
    @GetMapping("tree")
    public DimplesResponse getMenuTree(Menu menu) {
        MenuTreeDTO<Menu> menus = this.menuService.findMenus(menu);
        return DimplesResponse.success(menus.getChildren());
    }

    @ApiOperation(value = "新增菜单/按钮", notes = "新增菜单/按钮")
    @OpsLog(value = "新增菜单/按钮", type = OpsLogTypeEnum.ADD)
    @PostMapping
    @RequiresPermissions("menu:add")
    public DimplesResponse addMenu(@Valid Menu menu) {
        this.menuService.createMenu(menu);
        return DimplesResponse.success();
    }

    @ApiOperation(value = "删除菜单/按钮", notes = "删除菜单/按钮")
    @OpsLog(value = "删除菜单/按钮", type = OpsLogTypeEnum.ADD)
    @GetMapping("delete/{menuIds}")
    @RequiresPermissions("menu:delete")
    public DimplesResponse deleteMenus(@NotBlank(message = "{required}") @PathVariable String menuIds) {
        this.menuService.deleteMeuns(menuIds);
        return DimplesResponse.success();
    }

    @ApiOperation(value = "修改菜单/按钮", notes = "修改菜单/按钮")
    @OpsLog(value = "修改菜单/按钮", type = OpsLogTypeEnum.ADD)
    @PostMapping("update")
    @RequiresPermissions("menu:update")
    public DimplesResponse updateMenu(@Valid Menu menu) {
        this.menuService.updateMenu(menu);
        return DimplesResponse.success();
    }

    @ApiOperation(value = "导出菜单Excel", notes = "导出菜单Excel")
    @OpsLog(value = "导出菜单Excel", type = OpsLogTypeEnum.ADD)
    @GetMapping("excel")
    @RequiresPermissions("menu:export")
    public void export(Menu menu, HttpServletResponse response) {
        List<Menu> menus = this.menuService.findMenuList(menu);
        ExcelKit.$Export(Menu.class, response).downXlsx(menus, false);
    }
}
