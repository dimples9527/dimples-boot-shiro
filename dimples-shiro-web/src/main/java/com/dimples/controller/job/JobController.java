package com.dimples.controller.job;

import com.alibaba.excel.EasyExcel;
import com.dimples.common.annotation.OpsLog;
import com.dimples.common.controller.BaseController;
import com.dimples.core.constant.DimplesConstant;
import com.dimples.core.eunm.OpsLogTypeEnum;
import com.dimples.core.transport.DimplesResponse;
import com.dimples.core.transport.QueryRequest;
import com.dimples.job.po.Job;
import com.dimples.job.service.JobService;
import com.dimples.system.dto.UserDetailDTO;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.CronExpression;
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
import javax.validation.Valid;
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
@Api(tags = "任务调度模块")
@Validated
@RestController
@RequestMapping("job")
public class JobController extends BaseController {

    private JobService jobService;

    @Autowired
    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @ApiOperation(value = "获取任务列表", notes = "获取任务列表")
    @OpsLog(value = "获取任务列表", type = OpsLogTypeEnum.SELECT)
    @GetMapping
    @RequiresPermissions("job:view")
    public DimplesResponse jobList(QueryRequest request, Job job) {
        Map<String, Object> dataTable = getDataTable(this.jobService.findJobs(request, job));
        return DimplesResponse.success(dataTable);
    }

    @ApiOperation(value = "新增定时任务", notes = "新增定时任务")
    @OpsLog(value = "新增定时任务", type = OpsLogTypeEnum.ADD)
    @PostMapping
    @RequiresPermissions("job:add")
    public DimplesResponse jobSave(@Valid Job job) {
        boolean save = this.jobService.createJob(job);
        return save ? DimplesResponse.success() : DimplesResponse.failed();
    }

    @ApiOperation(value = "删除定时任务", notes = "删除定时任务")
    @ApiImplicitParam(name = "jobIds", value = "任务id(多个之间以|分隔)", paramType = "string", required = true)
    @OpsLog(value = "删除定时任务", type = OpsLogTypeEnum.DELETE)
    @PostMapping("delete/{jobIds}")
    @RequiresPermissions("job:delete")
    public DimplesResponse deleteJob(@NotBlank(message = "jobIds不能为空") @PathVariable String jobIds) {
        this.jobService.deleteJobs(jobIds);
        return DimplesResponse.success();
    }

    @ApiOperation(value = "修改定时任务", notes = "修改定时任务")
    @OpsLog(value = "修改定时任务", type = OpsLogTypeEnum.UPDATE)
    @PostMapping("update")
    public DimplesResponse updateJob(@Valid Job job) {
        this.jobService.updateJob(job);
        return DimplesResponse.success();
    }

    @ApiOperation(value = "执行定时任务", notes = "执行定时任务")
    @ApiImplicitParam(name = "jobIds", value = "任务id(多个之间以|分隔)", paramType = "string", required = true)
    @OpsLog(value = "执行定时任务", type = OpsLogTypeEnum.START)
    @PostMapping("run/{jobIds}")
    @RequiresPermissions("job:run")
    public DimplesResponse runJob(@NotBlank(message = "jobIds不能为空") @PathVariable String jobIds) {
        this.jobService.run(jobIds);
        return DimplesResponse.success();
    }

    @ApiOperation(value = "暂停定时任务", notes = "暂停定时任务")
    @ApiImplicitParam(name = "jobIds", value = "任务id(多个之间以|分隔)", paramType = "string", required = true)
    @OpsLog(value = "暂停定时任务", type = OpsLogTypeEnum.PAUSE)
    @PostMapping("pause/{jobIds}")
    @RequiresPermissions("job:pause")
    public DimplesResponse pauseJob(@NotBlank(message = "jobIds不能为空") @PathVariable String jobIds) {
        int pause = this.jobService.pause(jobIds);
        return pause > 0 ? DimplesResponse.success() : DimplesResponse.failed();
    }

    @ApiOperation(value = "恢复定时任务", notes = "恢复定时任务")
    @ApiImplicitParam(name = "jobIds", value = "任务id(多个之间以|分隔)", paramType = "string", required = true)
    @OpsLog(value = "恢复定时任务", type = OpsLogTypeEnum.RESUME)
    @PostMapping("resume/{jobIds}")
    @RequiresPermissions("job:resume")
    public DimplesResponse resumeJob(@NotBlank(message = "{required}") @PathVariable String jobIds) {
        this.jobService.resume(jobIds);
        return DimplesResponse.success();
    }

    @ApiOperation(value = "导出任务列表", notes = "导出任务列表")
    @OpsLog(value = "导出任务列表", type = OpsLogTypeEnum.EXPORT)
    @GetMapping("excel")
    @RequiresPermissions("job:export")
    public void export(QueryRequest request, Job job, HttpServletResponse response) throws Exception {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easy-excel没有关系
        String fileName = URLEncoder.encode("任务信息", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + DimplesConstant.VALID_FILE_TYPE[0]);
        // 获取数据
        List<Job> jobs = this.jobService.findJobs(request, job).getRecords();
        EasyExcel.write(response.getOutputStream(), UserDetailDTO.class).sheet("任务信息").doWrite(jobs);
    }

    @ApiOperation(value = "校验cron表达式", notes = "校验cron表达式")
    @GetMapping("cron/check")
    public boolean checkCron(String cron) {
        try {
            return CronExpression.isValidExpression(cron);
        } catch (Exception e) {
            return false;
        }
    }

}
