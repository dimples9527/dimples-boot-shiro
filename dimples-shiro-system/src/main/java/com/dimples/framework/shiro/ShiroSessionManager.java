package com.dimples.framework.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import java.io.Serializable;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/13
 */
@Slf4j
public class ShiroSessionManager extends DefaultWebSessionManager {

    private static final String AUTHORIZATION = "X-Token";
    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        //获取请求头中X-Token中保存的sessionId
        String id = WebUtils.toHttp(request).getHeader(AUTHORIZATION);
        if (!StringUtils.isEmpty(id)) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return id;
        } else {
            //否则默认从cookie中获取sessionId
            return super.getSessionId(request, response);
        }
    }
}
