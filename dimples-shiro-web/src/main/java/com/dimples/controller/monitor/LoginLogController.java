package com.dimples.controller.monitor;

import com.alibaba.excel.EasyExcel;
import com.dimples.common.controller.BaseController;
import com.dimples.common.annotation.OpsLog;
import com.dimples.core.constant.DimplesConstant;
import com.dimples.core.eunm.OpsLogTypeEnum;
import com.dimples.core.transport.DimplesResponse;
import com.dimples.core.transport.QueryRequest;
import com.dimples.monitor.po.LoginLog;
import com.dimples.monitor.service.LoginLogService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author MrBird
 */
@Slf4j
@Api(tags = "登陆日志管理模块")
@RestController
@RequestMapping("loginLog")
public class LoginLogController extends BaseController {

    private LoginLogService loginLogService;

    @Autowired
    public LoginLogController(LoginLogService loginLogService) {
        this.loginLogService = loginLogService;
    }

    @ApiOperation(value = "获取登陆日志列表", notes = "获取登陆日志列表")
    @GetMapping("list")
    @RequiresPermissions("login-log:view")
    @OpsLog(value = "获取登陆日志列表", type = OpsLogTypeEnum.SELECT)
    public DimplesResponse loginLogList(LoginLog loginLog, QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(this.loginLogService.findLoginLogs(loginLog, request));
        return DimplesResponse.success(dataTable);
    }

    @ApiOperation(value = "删除登陆日志", notes = "删除登陆日志")
    @ApiImplicitParam(name = "ids", value = "登陆日志对应id(多个以|分隔)", paramType = "query", required = true)
    @PostMapping("delete/{ids}")
    @RequiresPermissions("login-log:delete")
    @OpsLog(value = "删除登陆日志", type = OpsLogTypeEnum.DELETE)
    public DimplesResponse deleteLogsList(@NotBlank(message = "ids 不能为空") @PathVariable String ids) {
        this.loginLogService.deleteLoginLogs(ids);
        return DimplesResponse.success();
    }

    @GetMapping("excel")
    @RequiresPermissions("login-log:export")
    @OpsLog(value = "导出登陆日志", type = OpsLogTypeEnum.EXPORT)
    public void export(QueryRequest request, LoginLog loginLog, HttpServletResponse response) throws Exception {
        List<LoginLog> loginLogs = this.loginLogService.findLoginLogs(loginLog, request).getRecords();
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easy-excel没有关系
        String fileName = URLEncoder.encode("登陆日志", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + DimplesConstant.VALID_FILE_TYPE[0]);
        // 获取数据
        EasyExcel.write(response.getOutputStream(), LoginLog.class).sheet("用户信息").doWrite(loginLogs);
    }
}
