package com.dimples.biz.system.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import static org.junit.Assert.*;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class MenuServiceTest {

    @Resource
    private MenuService menuService;

    @Test
    public void findUserMenus() {
        menuService.findUserMenus("zhongyj");
    }
}