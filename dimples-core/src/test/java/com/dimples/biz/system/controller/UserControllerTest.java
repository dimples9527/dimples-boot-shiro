package com.dimples.biz.system.controller;

import com.dimples.biz.system.po.User;
import com.dimples.core.transport.QueryRequest;
import com.dimples.core.transport.DimplesResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserControllerTest {

    @Autowired
    private UserController userController;

    @Test
    public void userList() {
        User build = new User();
        DimplesResponse dimplesResponse = userController.userList(build, new QueryRequest());
        log.error("" + dimplesResponse.getData());
    }
}