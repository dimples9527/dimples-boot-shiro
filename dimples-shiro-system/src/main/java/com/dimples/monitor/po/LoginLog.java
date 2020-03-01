package com.dimples.monitor.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dimples.core.util.HttpContextUtil;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/1/28
 */
@Slf4j
@ApiModel(value = "com-dimples-biz-system-po-LoginLog")
@Data
@Builder
@TableName(value = "db_dimples_shiro.tb_login_log")
public class LoginLog implements Serializable {
    @TableId(value = "login_log_id", type = IdType.AUTO)
    @ApiModelProperty(value = "")
    private Long loginLogId;

    /**
     * 登陆用户
     */
    @TableField(value = "username")
    @ApiModelProperty(value = "登陆用户")
    private String username;

    /**
     * 登陆时间
     */
    @TableField(value = "login_time")
    @ApiModelProperty(value = "登陆时间")
    private Date loginTime;

    /**
     * 登陆地点
     */
    @TableField(value = "login_location")
    @ApiModelProperty(value = "登陆地点")
    private String loginLocation;

    /**
     * 登陆ip
     */
    @TableField(value = "login_ip")
    @ApiModelProperty(value = "登陆ip")
    private String loginIp;

    /**
     * 登陆操作系统
     */
    @TableField(value = "login_system")
    @ApiModelProperty(value = "登陆操作系统")
    private String loginSystem;

    /**
     * 登陆浏览器类型
     */
    @TableField(value = "login_browser")
    @ApiModelProperty(value = "登陆浏览器类型")
    private String loginBrowser;

    private static final long serialVersionUID = 1L;

    public void setSystemBrowserInfo() {
        try {
            HttpServletRequest request = HttpContextUtil.getRequest();

            StringBuilder userAgent = new StringBuilder("[");
            userAgent.append(request.getHeader("User-Agent"));
            userAgent.append("]");
            int indexOfMac = userAgent.indexOf("Mac OS X");
            int indexOfWindows = userAgent.indexOf("Windows NT");
            int indexOfIe = userAgent.indexOf("MSIE");
            int indexOfIe11 = userAgent.indexOf("rv:");
            int indexOfFirefox = userAgent.indexOf("Firefox");
            int indexOfSoGou = userAgent.indexOf("MetaSr");
            int indexOfChrome = userAgent.indexOf("Chrome");
            int indexOfSafari = userAgent.indexOf("Safari");
            boolean isMac = indexOfMac > 0;
            boolean isWindows = indexOfWindows > 0;
            boolean isLinux = userAgent.indexOf("Linux") > 0;
            boolean containIe = indexOfIe > 0 || (isWindows && (indexOfIe11 > 0));
            boolean containFirefox = indexOfFirefox > 0;
            boolean containSoGou = indexOfSoGou > 0;
            boolean containChrome = indexOfChrome > 0;
            boolean containSafari = indexOfSafari > 0;
            String browser = "";
            if (containSoGou) {
                if (containIe) {
                    browser = "搜狗" + userAgent.substring(indexOfIe, indexOfIe + "IE x.x".length());
                } else if (containChrome) {
                    browser = "搜狗" + userAgent.substring(indexOfChrome, indexOfChrome + "Chrome/xx".length());
                }
            } else if (containChrome) {
                browser = userAgent.substring(indexOfChrome, indexOfChrome + "Chrome/xx".length());
            } else if (containSafari) {
                int indexOfSafariVersion = userAgent.indexOf("Version");
                browser = "Safari "
                        + userAgent.substring(indexOfSafariVersion, indexOfSafariVersion + "Version/x.x.x.x".length());
            } else if (containFirefox) {
                browser = userAgent.substring(indexOfFirefox, indexOfFirefox + "Firefox/xx".length());
            } else if (containIe) {
                if (indexOfIe11 > 0) {
                    browser = "IE 11";
                } else {
                    browser = userAgent.substring(indexOfIe, indexOfIe + "IE x.x".length());
                }
            }
            String os = "";
            if (isMac) {
                os = userAgent.substring(indexOfMac, indexOfMac + "MacOS X xxxxxxxx".length());
            } else if (isLinux) {
                os = "Linux";
            } else if (isWindows) {
                os = "Windows ";
                String version = userAgent.substring(indexOfWindows + "Windows NT".length(), indexOfWindows
                        + "Windows NTx.x".length());
                switch (version.trim()) {
                    case "5.0":
                        os += "2000";
                        break;
                    case "5.1":
                        os += "XP";
                        break;
                    case "5.2":
                        os += "2003";
                        break;
                    case "6.0":
                        os += "Vista";
                        break;
                    case "6.1":
                        os += "7";
                        break;
                    case "6.2":
                        os += "8";
                        break;
                    case "6.3":
                        os += "8.1";
                        break;
                    case "10":
                        os += "10";
                        break;
                    default:
                }
            }
            this.loginSystem = os;
            this.loginBrowser = StringUtils.replace(browser, "/", " ");
        } catch (Exception e) {
            log.error("获取浏览器和登陆系统信息失败：{}", e.getMessage());
            this.loginSystem = "";
            this.loginBrowser = "";
        }

    }
}