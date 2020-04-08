package com.dimples.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dimples.job.po.JobLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JobLogMapper extends BaseMapper<JobLog> {
}