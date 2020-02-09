package com.dimples.biz.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dimples.biz.system.po.Dept;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/2/8
 */
@Mapper
public interface DeptMapper extends BaseMapper<Dept> {
    /**
     * 根据用户id查询用户部门信息
     *
     * @param userId 用户id
     * @return List<Dept>
     */
    List<Dept> findByUserId(@Param("userId") Long userId);
}