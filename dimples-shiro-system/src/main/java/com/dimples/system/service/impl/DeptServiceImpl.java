package com.dimples.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dimples.common.dto.DeptTreeDTO;
import com.dimples.common.util.TreeUtil;
import com.dimples.system.mapper.DeptMapper;
import com.dimples.system.po.Dept;
import com.dimples.system.service.DeptService;
import com.dimples.core.constant.DimplesConstant;
import com.dimples.core.transport.QueryRequest;
import com.dimples.core.util.SortUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/1/30
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    private DeptMapper deptMapper;

    @Autowired
    public DeptServiceImpl(DeptMapper deptMapper) {
        this.deptMapper = deptMapper;
    }

    @Override
    public List<Dept> findByUserId(Long userId) {
        return deptMapper.findByUserId(userId);
    }

    @Override
    public List<DeptTreeDTO<Dept>> findDeptList() {
        List<Dept> deptList = this.baseMapper.selectList(new QueryWrapper<>());
        List<DeptTreeDTO<Dept>> trees = TreeUtil.convertDeptList(deptList);
        return TreeUtil.buildDeptTree(trees);
    }

    @Override
    public List<DeptTreeDTO<Dept>> findDeptList(Dept dept) {
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(dept.getDeptName())) {
            queryWrapper.lambda().eq(Dept::getDeptName, dept.getDeptName());
        }
        queryWrapper.lambda().orderByAsc(Dept::getOrderNum);
        List<Dept> deptList = this.baseMapper.selectList(queryWrapper);
        List<DeptTreeDTO<Dept>> trees = TreeUtil.convertDeptList(deptList);
        return TreeUtil.buildDeptTree(trees);
    }

    @Override
    public List<Dept> findDeptList(Dept dept, QueryRequest request) {
        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(dept.getDeptName())) {
            queryWrapper.lambda().eq(Dept::getDeptName, dept.getDeptName());
        }
        SortUtil.handleWrapperSort(request, queryWrapper, "orderNum", DimplesConstant.ORDER_ASC, true);
        return this.baseMapper.selectList(queryWrapper);
    }

    @Override
    public void insertDept(Dept dept) {
        Long parentId = dept.getParentId();
        if (parentId == null) {
            dept.setParentId(0L);
        }
        dept.setCreateDate(new Date());
        this.save(dept);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteDeptList(String[] deptIds) {
        this.deleteTree(Arrays.asList(deptIds));
    }

    /**
     * 删除部门树
     *
     * @param deptIds 部门id 集合
     */
    private void deleteTree(List<String> deptIds) {
        this.removeByIds(deptIds);

        LambdaQueryWrapper<Dept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Dept::getParentId, deptIds);
        List<Dept> depts = baseMapper.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(depts)) {
            List<String> deptIdList = new ArrayList<>();
            depts.forEach(d -> deptIdList.add(String.valueOf(d.getDeptId())));
            this.deleteTree(deptIdList);
        }
    }

    @Override
    public void updateDept(Dept dept) {
        dept.setModifyDate(new Date());
        this.baseMapper.updateById(dept);
    }

}




















