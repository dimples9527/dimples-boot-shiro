package com.dimples.job.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dimples.core.transport.QueryRequest;
import com.dimples.job.po.JobLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/4/8
 */
public interface JobLogService extends IService<JobLog> {

    /**
     * 获取任务日志列表
     *
     * @param request QueryRequest
     * @param log     JobLog
     * @return IPage<JobLog>
     */
    IPage<JobLog> findJobLogs(QueryRequest request, JobLog log);

    /**
     * 删除调度日志
     *
     * @param jobIds String
     */
    void deleteJobLogs(String jobIds);
}
