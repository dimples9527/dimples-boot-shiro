package com.dimples.job.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dimples.core.constant.DimplesConstant;
import com.dimples.core.exception.DataException;
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
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteJobs(String jobIds) {
        String[] jobs = StringUtils.splitByWholeSeparator(jobIds, StringPool.PIPE);
        List<String> list = Arrays.asList(jobs);
        list.forEach(jobId -> ScheduleHelper.deleteScheduleJob(scheduler, Long.valueOf(jobId)));
        this.baseMapper.deleteBatchIds(list);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateJob(Job job) {
        if (ObjectUtils.isEmpty(job.getJobId())) {
            throw new DataException("更新任务时, jobId不能为空");
        }
        ScheduleHelper.updateScheduleJob(scheduler, job);
        this.baseMapper.updateById(job);
    }

    @Override
    public void run(String jobIds) {
        String[] list = StringUtils.splitByWholeSeparator(jobIds, StringPool.PIPE);
        Arrays.stream(list).forEach(jobId -> ScheduleHelper.run(scheduler, this.getById(Long.valueOf(jobId))));
    }

    @Override
    public int pause(String jobIds) {
        String[] list = StringUtils.splitByWholeSeparator(jobIds, StringPool.PIPE);
        Arrays.stream(list).forEach(jobId -> ScheduleHelper.pauseJob(scheduler, Long.valueOf(jobId)));
        return this.updateBatch(jobIds, Job.ScheduleStatus.PAUSE.getValue());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBatch(String jobIds, String status) {
        List<String> list = Arrays.asList(StringUtils.splitByWholeSeparator(jobIds, StringPool.PIPE));
        Job job = new Job();
        job.setStatus(status);
        return this.baseMapper.update(job, new LambdaQueryWrapper<Job>().in(Job::getJobId, list));
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void resume(String jobIds) {
        String[] list = StringUtils.splitByWholeSeparator(jobIds, StringPool.PIPE);
        Arrays.stream(list).forEach(jobId -> ScheduleHelper.resumeJob(scheduler, Long.valueOf(jobId)));
        this.updateBatch(jobIds, Job.ScheduleStatus.NORMAL.getValue());
    }
}












