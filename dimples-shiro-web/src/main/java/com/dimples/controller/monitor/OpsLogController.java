package com.dimples.controller.monitor;

import com.alibaba.excel.EasyExcel;
import com.dimples.common.controller.BaseController;
import com.dimples.core.constant.DimplesConstant;
import com.dimples.core.eunm.OpsLogTypeEnum;
import com.dimples.core.transport.DimplesResponse;
import com.dimples.core.transport.QueryRequest;
import com.dimples.monitor.po.LoginLog;
import com.dimples.monitor.po.OpsLog;
import com.dimples.monitor.service.OpsLogService;

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
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/4/2
 */
@Slf4j
@Api(tags = "操作日志模块")
@RestController
@RequestMapping("opsLog")
public class OpsLogController extends BaseController {

    private OpsLogService logService;

    @Autowired
    public OpsLogController(OpsLogService logService) {
        this.logService = logService;
    }

    @ApiOperation(value = "获取操作日志列表", notes = "获取操作日志列表")
    @com.dimples.common.annotation.OpsLog(value = "获取操作日志列表", type = OpsLogTypeEnum.SELECT)
    @RequiresPermissions("ops-log:view")
    @GetMapping("list")
    public DimplesResponse logList(OpsLog log, QueryRequest request) {
        Map<String, Object> dataTable = getDataTable(this.logService.listLogs(log, request));
        return DimplesResponse.success(dataTable);
    }

    @ApiOperation(value = "删除操作日志", notes = "删除操作日志")
    @ApiImplicitParam(name = "ids", value = "待删除日志id(多个之间以|分隔)", paramType = "path", required = true)
    @com.dimples.common.annotation.OpsLog(value = "删除操作日志", type = OpsLogTypeEnum.DELETE)
    @PostMapping("delete/{ids}")
    @RequiresPermissions("ops-log:delete")
    public DimplesResponse deleteLogs(@NotBlank(message = "ids不能为空") @PathVariable String ids) {
        this.logService.deleteLogs(ids);
        return DimplesResponse.success();
    }

    @ApiOperation(value = "导出操作日志", notes = "导出操作日志")
    @com.dimples.common.annotation.OpsLog(value = "导出操作日志", type = OpsLogTypeEnum.EXPORT)
    @GetMapping("excel")
    @RequiresPermissions("ops-log:export")
    public void export(QueryRequest request, OpsLog loginLog, HttpServletResponse response) throws Exception {
        List<OpsLog> loginLogs = this.logService.listLogs(loginLog, request).getRecords();
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easy-excel没有关系
        String fileName = URLEncoder.encode("操作日志", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + DimplesConstant.VALID_FILE_TYPE[0]);
        // 获取数据
        EasyExcel.write(response.getOutputStream(), LoginLog.class).sheet("操作日志").doWrite(loginLogs);
    }

}












