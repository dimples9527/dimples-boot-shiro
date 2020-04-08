package com.dimples.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dimples.core.constant.DimplesConstant;
import com.dimples.core.transport.QueryRequest;
import com.dimples.core.util.SortUtil;
import com.dimples.job.helper.ScheduleHelper;
import com.dimples.job.mapper.JobMapper;
import com.dimples.job.po.Job;
import com.dimples.job.service.JobService;

import org.apache.commons.lang3.StringUtils;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/4/6
 */
@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements JobService {

    private Scheduler scheduler;

    @Autowired
    public JobServiceImpl(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Override
    public IPage<Job> findJobs(QueryRequest request, Job job) {
        LambdaQueryWrapper<Job> queryWrapper = new LambdaQueryWrapper<>();

        if (StringUtils.isNotBlank(job.getBeanName())) {
            queryWrapper.eq(Job::getBeanName, job.getBeanName());
        }
        if (StringUtils.isNotBlank(job.getMethodName())) {
            queryWrapper.eq(Job::getMethodName, job.getMethodName());
        }
        if (StringUtils.isNotBlank(job.getParams())) {
            queryWrapper.like(Job::getParams, job.getParams());
        }
        if (StringUtils.isNotBlank(job.getRemark())) {
            queryWrapper.like(Job::getRemark, job.getRemark());
        }
        if (StringUtils.isNotBlank(job.getStatus())) {
            queryWrapper.eq(Job::getStatus, job.getStatus());
        }

        if (StringUtils.isNotBlank(job.getCreateTimeFrom()) && StringUtils.isNotBlank(job.getCreateTimeTo())) {
            queryWrapper
                    .ge(Job::getCreateTime, job.getCreateTimeFrom())
                    .le(Job::getCreateTime, job.getCreateTimeTo());
        }
        Page<Job> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "createTime", DimplesConstant.ORDER_DESC, true);
        return this.page(page, queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean createJob(Job job) {
        job.setCreateTime(new Date());
        job.setStatus(Job.ScheduleStatus.PAUSE.getValue());
        boolean save = this.save(job);
        ScheduleHelper.createScheduleJob(scheduler, job);
        return save;
    }
}












