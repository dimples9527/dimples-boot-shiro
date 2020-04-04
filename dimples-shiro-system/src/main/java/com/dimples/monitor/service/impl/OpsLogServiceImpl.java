package com.dimples.monitor.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dimples.core.constant.DimplesConstant;
import com.dimples.core.transport.QueryRequest;
import com.dimples.core.util.SortUtil;
import com.dimples.monitor.mapper.OpsLogMapper;
import com.dimples.monitor.po.OpsLog;
import com.dimples.monitor.service.OpsLogService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/3/31
 */
@Service
public class OpsLogServiceImpl extends ServiceImpl<OpsLogMapper, OpsLog> implements OpsLogService {

    @Override
    public IPage<OpsLog> listLogs(OpsLog log, QueryRequest request) {
        QueryWrapper<OpsLog> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(log.getUsername())) {
            queryWrapper.lambda().eq(OpsLog::getUsername, log.getUsername().toLowerCase());
        }
        if (StringUtils.isNotBlank(log.getOperation())) {
            queryWrapper.lambda().like(OpsLog::getOperation, log.getOperation());
        }
        if (StringUtils.isNotBlank(log.getLocation())) {
            queryWrapper.lambda().like(OpsLog::getLocation, log.getLocation());
        }
        if (StringUtils.isNotBlank(log.getCreateTimeFrom()) && StringUtils.isNotBlank(log.getCreateTimeTo())) {
            queryWrapper.lambda()
                    .ge(OpsLog::getCreateTime, log.getCreateTimeFrom())
                    .le(OpsLog::getCreateTime, log.getCreateTimeTo());
        }

        Page<OpsLog> page = new Page<>(request.getPageNum(), request.getPageSize());
        SortUtil.handlePageSort(request, page, "createTime", DimplesConstant.ORDER_DESC, true);

        return this.page(page, queryWrapper);
    }

    @Override
    public void deleteLogs(String ids) {
        List<String> list = Arrays.asList(StringUtils.splitByWholeSeparator(ids, StringPool.PIPE));
        this.baseMapper.deleteBatchIds(list);
    }
}
