package com.dimples.controller.system;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dimples.common.controller.BaseController;
import com.dimples.core.annotation.OpsLog;
import com.dimples.core.eunm.OpsLogTypeEnum;
import com.dimples.core.exception.BizException;
import com.dimples.core.transport.DimplesResponse;
import com.dimples.core.transport.QueryRequest;
import com.dimples.system.dto.UserDetailDTO;
import com.dimples.system.po.User;
import com.dimples.system.service.UserService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 用户管理
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/14
 */
@Slf4j
@Validated
@Api(value = "用户管理模块", tags = "用户管理模块")
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ApiOperation(value = "增加用户", notes = "增加用户")
    @OpsLog(value = "增加用户", type = OpsLogTypeEnum.ADD)
    @PostMapping()
    public DimplesResponse add(@Valid UserDetailDTO user) {
        this.userService.add(user);
        return DimplesResponse.success();
    }

    @ApiOperation(value = "用户注册", notes = "用户注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", paramType = "string", dataType = "query", required = true),
            @ApiImplicitParam(name = "password", value = "密码", paramType = "string", dataType = "query", required = true)
    })
    @OpsLog(value = "用户注册", type = OpsLogTypeEnum.ADD)
    @PostMapping("register")
    public DimplesResponse register(
            @NotBlank(message = "{required}") String username,
            @NotBlank(message = "{required}") String password) throws BizException {
        User user = userService.findByName(username);
        if (user != null) {
            throw new BizException("该用户名已存在");
        }
        this.userService.register(username, password);
        return DimplesResponse.success();
    }

    @ApiOperation(value = "用户查询", notes = "用户查询")
    @OpsLog(value = "分页查询用户", type = OpsLogTypeEnum.SELECT)
    @GetMapping("list")
    public DimplesResponse userList(User user, QueryRequest request) {
        IPage<UserDetailDTO> userList = this.userService.findUserDetailList(user, request);
        return DimplesResponse.success(userList);
    }

    @ApiOperation(value = "检查用户名是否存在", notes = "检查用户名是否存在")
    @OpsLog(value = "检查用户名是否存在", type = OpsLogTypeEnum.SELECT)
    @GetMapping("check/{username}")
    public boolean checkUserName(@NotBlank(message = "{required}") @PathVariable String username) {
        return this.userService.findByName(username) == null;
    }

    @ApiOperation(value = "更新用户信息", notes = "更新用户信息")
    @OpsLog(value = "更新用户信息", type = OpsLogTypeEnum.UPDATE)
    @RequiresPermissions("user:update")
    @PostMapping("update")
    public DimplesResponse update(@Valid UserDetailDTO userDetailDTO) {
        this.userService.updateUser(userDetailDTO);
        return DimplesResponse.success();
    }

    @ApiOperation(value = "删除用户", notes = "删除用户")
    @ApiImplicitParam(name = "userIds", value = "用户id(多个以|分隔)", paramType = "query")
    @OpsLog(value = "删除用户", type = OpsLogTypeEnum.DELETE)
    @RequiresPermissions("user:delete")
    @PostMapping("delete/{userIds}")
    public DimplesResponse delete(@NotBlank(message = "userIds不能为空") @PathVariable String userIds) {
        this.userService.deleteUsers(userIds);
        return DimplesResponse.success();
    }

    @GetMapping("excel")
    @RequiresPermissions("user:export")
    public void export(QueryRequest queryRequest, User user, HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easy-excel没有关系
        String fileName = URLEncoder.encode("测试", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        // 获取数据
        List<UserDetailDTO> userDetailS = this.userService.findUserDetailList(user, queryRequest).getRecords();
        EasyExcel.write(response.getOutputStream(), UserDetailDTO.class).sheet("模板").doWrite(userDetailS);
    }

}
















