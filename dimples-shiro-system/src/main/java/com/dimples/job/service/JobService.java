package com.dimples.job.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dimples.core.transport.QueryRequest;
import com.dimples.job.po.Job;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/4/6
 */
public interface JobService extends IService<Job> {


    /**
     * 分页查找任务信息
     *
     * @param request QueryRequest
     * @param job     Job
     * @return IPage<Job>
     */
    IPage<Job> findJobs(QueryRequest request, Job job);

    /**
     * 创建定时任务
     *
     * @param job Job
     * @return boolean
     */
    boolean createJob(Job job);
}
