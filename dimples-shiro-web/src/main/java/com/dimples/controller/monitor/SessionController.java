package com.dimples.controller.monitor;

import com.dimples.core.annotation.OpsLog;
import com.dimples.core.eunm.OpsLogTypeEnum;
import com.dimples.core.transport.DimplesResponse;
import com.dimples.monitor.po.ActiveUser;
import com.dimples.monitor.service.SessionService;
import com.google.common.collect.Maps;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author MrBird
 */
@RestController
@RequestMapping("session")
public class SessionController {

    private SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @OpsLog(value = "获取在线用户列表", type = OpsLogTypeEnum.SELECT)
    @GetMapping("list")
    @RequiresPermissions("online:view")
    public DimplesResponse list(String username) {
        List<ActiveUser> list = sessionService.list(username);
        Map<String, Object> data = Maps.newHashMap();
        data.put("records", list);
        data.put("total", CollectionUtils.size(list));
        return DimplesResponse.success(data);
    }

    @OpsLog(value = "获取在线用户列表", type = OpsLogTypeEnum.LOGOUT)
    @PostMapping("delete/{id}")
    @RequiresPermissions("user:kickout")
    public DimplesResponse forceLogout(@PathVariable String id) {
        sessionService.forceLogout(id);
        return DimplesResponse.success();
    }
}
