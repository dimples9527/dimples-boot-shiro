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

    /**
     * 删除任务
     *
     * @param jobIds 任务id
     */
    void deleteJobs(String jobIds);

    /**
     * 批量更新
     *
     * @param jobIds String
     * @param status String
     * @return 结果
     */
    int updateBatch(String jobIds, String status);

    /**
     * 更新任务
     *
     * @param job Job
     */
    void updateJob(Job job);

    /**
     * 启动任务
     *
     * @param jobIds String
     */
    void run(String jobIds);

    /**
     * 暂停任务
     *
     * @param jobIds String
     * @return int
     */
    int pause(String jobIds);

    /**
     * 恢复定时任务
     *
     * @param jobIds String
     */
    void resume(String jobIds);
}
