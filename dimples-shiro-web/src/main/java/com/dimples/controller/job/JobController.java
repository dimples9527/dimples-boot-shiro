package com.dimples.controller.job;

import com.dimples.common.annotation.OpsLog;
import com.dimples.common.controller.BaseController;
import com.dimples.core.eunm.OpsLogTypeEnum;
import com.dimples.core.transport.DimplesResponse;
import com.dimples.core.transport.QueryRequest;
import com.dimples.job.po.Job;
import com.dimples.job.service.JobService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.quartz.CronExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import javax.validation.Valid;

import io.swagger.annotations.Api;
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
