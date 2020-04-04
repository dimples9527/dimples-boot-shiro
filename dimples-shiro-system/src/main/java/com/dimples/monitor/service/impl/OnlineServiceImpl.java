package com.dimples.monitor.service.impl;

import com.dimples.core.util.AddressUtil;
import com.dimples.core.util.DateUtil;
import com.dimples.monitor.po.OnlineUser;
import com.dimples.monitor.service.OnlineService;
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
public class OnlineServiceImpl implements OnlineService {

    private SessionDAO sessionDAO;

    @Autowired
    public OnlineServiceImpl(SessionDAO sessionDAO) {
        this.sessionDAO = sessionDAO;
    }

    @Override
    public List<OnlineUser> list(String username) {
        String currentSessionId = (String) SecurityUtils.getSubject().getSession().getId();

        List<OnlineUser> list = new ArrayList<>();
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {
            OnlineUser onlineUser = new OnlineUser();
            User user = new User();
            SimplePrincipalCollection principalCollection;
            if (session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY) == null) {
                continue;
            } else {
                principalCollection = (SimplePrincipalCollection) session
                        .getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
                BeanUtils.copyProperties(principalCollection.getPrimaryPrincipal(), user);
                onlineUser.setUsername(user.getUsername());
                onlineUser.setUserId(user.getUserId().toString());
            }
            onlineUser.setId((String) session.getId());
            onlineUser.setHost(session.getHost());
            onlineUser.setStartTimestamp(DateUtil.getDateFormat(session.getStartTimestamp(), DateUtil.YYYY_MM_DD_HH_MM_SS));
            onlineUser.setLastAccessTime(DateUtil.getDateFormat(session.getLastAccessTime(), DateUtil.YYYY_MM_DD_HH_MM_SS));
            long timeout = session.getTimeout();
            onlineUser.setStatus(timeout == 0L ? "0" : "1");
            String address = AddressUtil.getCityInfo(onlineUser.getHost());
            onlineUser.setLocation(address);
            onlineUser.setTimeout(timeout);
            if (StringUtils.equals(currentSessionId, onlineUser.getId())) {
                onlineUser.setCurrent(true);
            }
            if (StringUtils.isBlank(username)
                    || StringUtils.equalsIgnoreCase(onlineUser.getUsername(), username)) {
                list.add(onlineUser);
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
