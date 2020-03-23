package com.dimples.monitor.service.impl;

import com.dimples.core.util.AddressUtil;
import com.dimples.core.util.DateUtil;
import com.dimples.monitor.po.ActiveUser;
import com.dimples.monitor.service.SessionService;
import com.dimples.system.po.User;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author MrBird
 */
@Service
public class SessionServiceImpl implements SessionService {

    private SessionDAO sessionDAO;

    @Autowired
    public SessionServiceImpl(SessionDAO sessionDAO) {
        this.sessionDAO = sessionDAO;
    }

    @Override
    public List<ActiveUser> list(String username) {
        String currentSessionId = (String) SecurityUtils.getSubject().getSession().getId();

        List<ActiveUser> list = new ArrayList<>();
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {
            ActiveUser activeUser = new ActiveUser();
            User user = new User();
            SimplePrincipalCollection principalCollection;
            if (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) {
                continue;
            } else {
                principalCollection = (SimplePrincipalCollection) session
                        .getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                BeanUtils.copyProperties(principalCollection.getPrimaryPrincipal(), user);
                activeUser.setUsername(user.getUsername());
                activeUser.setUserId(user.getUserId().toString());
            }
            activeUser.setId((String) session.getId());
            activeUser.setHost(session.getHost());
            activeUser.setStartTimestamp(DateUtil.getDateFormat(session.getStartTimestamp(), DateUtil.YYYY_MM_DD_HH_MM_SS));
            activeUser.setLastAccessTime(DateUtil.getDateFormat(session.getLastAccessTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
            long timeout = session.getTimeout();
            activeUser.setStatus(timeout == 0L ? "0" : "1");
            String address = AddressUtil.getCityInfo(activeUser.getHost());
            activeUser.setLocation(address);
            activeUser.setTimeout(timeout);
            if (StringUtils.equals(currentSessionId, activeUser.getId())) {
                activeUser.setCurrent(true);
            }
            if (StringUtils.isBlank(username)
                    || StringUtils.equalsIgnoreCase(activeUser.getUsername(), username)) {
                list.add(activeUser);
            }
        }
        return list;
    }

    @Override
    public void forceLogout(String sessionId) {
        Session session = sessionDAO.readSession(sessionId);
        session.setTimeout(0);
        session.stop();
        sessionDAO.delete(session);
    }
}
