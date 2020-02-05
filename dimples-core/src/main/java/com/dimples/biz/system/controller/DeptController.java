package com.dimples.biz.system.controller;

import com.dimples.biz.common.dto.DeptTreeDTO;
import com.dimples.biz.system.po.Dept;
import com.dimples.biz.system.service.DeptService;
import com.dimples.core.annotation.OpsLog;
import com.dimples.core.eunm.OpsLogTypeEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/2/5
 */
@Slf4j
@Api(value = "部门管理模块", tags = "部门管理模块")
@RestController
@RequestMapping("dept")
public class DeptController {

    private DeptService deptService;

    @Autowired
    public DeptController(DeptService deptService) {
        this.deptService = deptService;
    }

    @ApiOperation(value = "获取部门树", notes = "获取部门树")
    @OpsLog(value = "获取部门树", type = OpsLogTypeEnum.SELECT)
    @GetMapping("select/tree")
    public List<DeptTreeDTO<Dept>> getDeptTree() {
        return this.deptService.findDeptList();
    }

}













