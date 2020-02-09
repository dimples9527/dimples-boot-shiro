package com.dimples.biz.system.service;

import com.dimples.biz.system.po.Dept;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DeptServiceTest {

    @Autowired
    private DeptService deptService;

    @Test
    public void findDeptList() {
        deptService.findDeptList();
    }

    @Test
    public void testFindDeptList() {
        Dept dept = new Dept();
        dept.setDeptName("研发部");
        deptService.findDeptList();
    }
}