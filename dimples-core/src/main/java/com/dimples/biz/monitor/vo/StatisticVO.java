package com.dimples.biz.monitor.vo;

import com.dimples.biz.monitor.po.LoginLog;
import com.dimples.biz.system.po.Dept;
import com.dimples.biz.system.po.Role;
import com.dimples.core.util.DateUtil;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Data;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/1/29
 */
@Data
@Builder
public class StatisticVO {

    private Integer todayIpTotal;

    private Integer todayTotal;

    private Integer loginTotal;

    private String lastLoginTime;

    private String dept;

    private String role;

    public void buildLastLoginTime(List<LoginLog> loginLog) {
        if (loginLog.size() > 1) {
            String formatDate = DateUtil.format(loginLog.get(1).getLoginTime(), DateUtil.YYYY_MM_DD_HH_MM_SS);
            String[] string = StringUtils.splitByWholeSeparatorPreserveAllTokens(formatDate, null);
            String[] stringD = StringUtils.splitByWholeSeparatorPreserveAllTokens(string[0], "-");
            String[] stringT = StringUtils.splitByWholeSeparatorPreserveAllTokens(string[1], ":");
            this.lastLoginTime = stringD[0] + "年" + stringD[1] + "月" + stringD[2] + "日 "
                    + stringT[0] + "时" + stringT[1] + "分" + stringT[2] + "秒";
        } else {
            this.lastLoginTime = "第一次访问系统";
        }

    }

    public void buildRole(List<Role> byUserId) {
        if (byUserId.size() > 0) {
            List<String> collect = byUserId.stream()
                    .map(Role::getRoleName)
                    .collect(Collectors.toList());
            this.role = StringUtils.join(collect, ",");
        } else {
            this.role = "暂无角色";
        }
    }

    public void buildDept(List<Dept> byUserId) {
        if (byUserId.size() > 0) {
            List<String> collect = byUserId.stream()
                    .map(Dept::getDeptName)
                    .collect(Collectors.toList());
            this.dept = StringUtils.join(collect, ",");
        } else {
            this.dept = "暂无所属部门";
        }
    }
}
















