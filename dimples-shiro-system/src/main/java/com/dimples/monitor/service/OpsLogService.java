package com.dimples.monitor.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dimples.core.transport.QueryRequest;
import com.dimples.monitor.po.OpsLog;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/3/31
 */
public interface OpsLogService extends IService<OpsLog> {

    /**
     * 查询操作日志分页
     *
     * @param log     日志
     * @param request QueryRequest
     * @return IPage<SystemLog>
     */
    IPage<OpsLog> listLogs(OpsLog log, QueryRequest request);

    /**
     * 删除操作日志
     *
     * @param ids 日志 ID 字符串
     */
    void deleteLogs(String ids);
}
