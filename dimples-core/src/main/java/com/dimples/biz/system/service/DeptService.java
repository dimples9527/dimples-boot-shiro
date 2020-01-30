package com.dimples.biz.system.service;

import com.dimples.biz.system.po.Dept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/1/30
 */
public interface DeptService extends IService<Dept> {

    /**
     * 根据用户id查询用户部门信息
     *
     * @param userId 用户id
     * @return List<Dept>
     */
    List<Dept> findByUserId(Long userId);

}
