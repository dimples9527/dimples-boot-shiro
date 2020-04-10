package com.dimples.controller.job;

import com.alibaba.excel.EasyExcel;
import com.dimples.common.annotation.OpsLog;
import com.dimples.common.controller.BaseController;
import com.dimples.core.constant.DimplesConstant;
import com.dimples.core.eunm.OpsLogTypeEnum;
import com.dimples.core.transport.DimplesResponse;
import com.dimples.core.transport.QueryRequest;
import com.dimples.job.po.JobLog;
import com.dimples.job.service.JobLogService;
import com.dimples.system.dto.UserDetailDTO;

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
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/4/6
 */
@Slf4j
@Api(tags = "任务日志模块")
@Validated
@RestController
@RequestMapping("jobLog")
public class JobLogController extends BaseController {

    private JobLogService jobLogService;

    @Autowired
    public JobLogController(JobLogService jobLogService) {
        this.jobLogService = jobLogService;
    }

    @ApiOperation(value = "获取任务日志列表", notes = "获取任务日志列表")
    @OpsLog(value = "获取任务日志列表", type = OpsLogTypeEnum.SELECT)
    @GetMapping
    @RequiresPermissions("job:log:view")
    public DimplesResponse jobList(QueryRequest request, JobLog log) {
        Map<String, Object> dataTable = getDataTable(this.jobLogService.findJobLogs(request, log));
        return DimplesResponse.success(dataTable);
    }

    @ApiOperation(value = "删除调度日志失败", notes = "删除调度日志失败")
    @ApiImplicitParam(name = "jobIds", value = "调度日志id(多个以|分隔)", paramType = "string", required = true)
    @OpsLog(value = "删除调度日志失败", type = OpsLogTypeEnum.DELETE)
    @PostMapping("delete/{jobIds}")
    @RequiresPermissions("job:log:delete")
    public DimplesResponse deleteJobLog(@NotBlank(message = "jobIds不能为空") @PathVariable String jobIds) {
        this.jobLogService.deleteJobLogs(jobIds);
        return DimplesResponse.success();
    }

    @ApiOperation(value = "导出调度日志", notes = "导出调度日志")
    @OpsLog(value = "导出调度日志", type = OpsLogTypeEnum.EXPORT)
    @GetMapping("excel")
    @RequiresPermissions("job:log:export")
    public void export(QueryRequest request, JobLog jobLog, HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easy-excel没有关系
        String fileName = URLEncoder.encode("导出调度日志信息", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + DimplesConstant.VALID_FILE_TYPE[0]);
        // 获取数据
        List<JobLog> jobLogs = this.jobLogService.findJobLogs(request, jobLog).getRecords();
        EasyExcel.write(response.getOutputStream(), UserDetailDTO.class).sheet("导出调度日志").doWrite(jobLogs);
    }

}
