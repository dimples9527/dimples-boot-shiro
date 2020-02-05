package com.dimples.biz.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dimples.biz.common.dto.DeptTreeDTO;
import com.dimples.biz.common.util.TreeUtil;
import com.dimples.biz.system.mapper.DeptMapper;
import com.dimples.biz.system.po.Dept;
import com.dimples.biz.system.service.DeptService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<DeptTreeDTO<Dept>> trees = this.convertDeptList(deptList);
        return TreeUtil.buildDeptTree(trees);
    }

    private List<DeptTreeDTO<Dept>> convertDeptList(List<Dept> depts) {
        List<DeptTreeDTO<Dept>> trees = new ArrayList<>();
        depts.forEach(dept -> {
            DeptTreeDTO<Dept> tree = new DeptTreeDTO<>();
            tree.setId(String.valueOf(dept.getDeptId()));
            tree.setParentId(String.valueOf(dept.getParentId()));
            tree.setName(dept.getDeptName());
            tree.setData(dept);
            trees.add(tree);
        });
        return trees;
    }
}



















