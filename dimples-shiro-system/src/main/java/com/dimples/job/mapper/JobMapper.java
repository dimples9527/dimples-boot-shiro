package com.dimples.job.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dimples.job.po.Job;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JobMapper extends BaseMapper<Job> {
}