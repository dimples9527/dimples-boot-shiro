package com.dimples.controller.system;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.dimples.common.dto.DeptTreeDTO;
import com.dimples.core.annotation.OpsLog;
import com.dimples.core.eunm.OpsLogTypeEnum;
import com.dimples.core.transport.DimplesResponse;
import com.dimples.core.transport.QueryRequest;
import com.dimples.system.po.Dept;
import com.dimples.system.service.DeptService;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
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

    @ApiOperation(value = "根据条件获取部门树", notes = "根据条件获取部门树")
    @OpsLog(value = "根据条件获取部门树", type = OpsLogTypeEnum.SELECT)
    @GetMapping("tree")
    public DimplesResponse getDeptTree(Dept dept) {
        List<DeptTreeDTO<Dept>> deptList = this.deptService.findDeptList(dept);
        return DimplesResponse.success(deptList);
    }

    @ApiOperation(value = "新增部门", notes = "新增部门")
    @OpsLog(value = "新增部门", type = OpsLogTypeEnum.ADD)
    @PostMapping
    @RequiresPermissions("dept:add")
    public DimplesResponse addDept(@Valid Dept dept) {
        this.deptService.insertDept(dept);
        return DimplesResponse.success();
    }

    @ApiOperation(value = "删除部门", notes = "删除部门")
    @ApiImplicitParam(name = "deptIds", value = "部门字符串，以,分隔", paramType = "string", dataType = "path", required = true,example = "{key:}")
    @OpsLog(value = "删除部门", type = OpsLogTypeEnum.DELETE)
    @DeleteMapping("delete/{deptIds}")
    @RequiresPermissions("dept:delete")
    public DimplesResponse deleteDepts(@NotBlank(message = "{required}") @PathVariable String deptIds) {
        String[] ids = deptIds.split(StringPool.COMMA);
        this.deptService.deleteDeptList(ids);
        return DimplesResponse.success();
    }

    @ApiOperation(value = "修改部门", notes = "修改部门")
    @OpsLog(value = "修改部门", type = OpsLogTypeEnum.UPDATE)
    @PostMapping("update")
    @RequiresPermissions("dept:update")
    public DimplesResponse updateDept(@Valid Dept dept) {
        this.deptService.updateDept(dept);
        return DimplesResponse.success();
    }

    @ApiOperation(value = "导出部门Excel", notes = "导出部门Excel")
    @OpsLog(value = "导出部门Excel", type = OpsLogTypeEnum.EXPORT)
    @GetMapping("excel")
    @RequiresPermissions("dept:export")
    public void export(Dept dept, QueryRequest request, HttpServletResponse response) {

    }

}













