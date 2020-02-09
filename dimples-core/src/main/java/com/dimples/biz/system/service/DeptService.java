package com.dimples.biz.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dimples.biz.common.dto.DeptTreeDTO;
import com.dimples.biz.system.po.Dept;
import com.dimples.core.transport.QueryRequest;

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

    /**
     * 获取部门树（下拉选使用）
     *
     * @return 部门树集合
     */
    List<DeptTreeDTO<Dept>> findDeptList();

    /**
     * 获取部门树（下拉选使用）
     *
     * @param dept 部门对象（传递查询参数）
     * @return 部门树集合
     */
    List<DeptTreeDTO<Dept>> findDeptList(Dept dept);

    /**
     * 获取部门树（供Excel导出）
     *
     * @param dept    部门对象（传递查询参数）
     * @param request QueryRequest
     * @return List<Dept>
     */
    List<Dept> findDeptList(Dept dept, QueryRequest request);

    /**
     * 新增部门
     *
     * @param dept 部门对象
     */
    void insertDept(Dept dept);

    /**
     * 删除部门
     *
     * @param deptIds 部门 ID数组
     */
    void deleteDeptList(String[] deptIds);

    /**
     * 修改部门
     *
     * @param dept 部门对象
     */
    void updateDept(Dept dept);

}


