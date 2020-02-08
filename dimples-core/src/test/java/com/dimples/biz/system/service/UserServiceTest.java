package com.dimples.biz.system.service;

import com.dimples.biz.system.po.User;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void findByName() {
        User name = userService.findByName("zhongyj");
        log.info("根据用户名查询用户信息：{}", name);
    }

    @Test
    public void findUserDetailList() {
    }

}















