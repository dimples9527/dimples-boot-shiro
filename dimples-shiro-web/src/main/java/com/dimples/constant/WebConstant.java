package com.dimples.constant;

/**
 * 页面名称常量类
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/1/21
 */
public class WebConstant {

    /**
     * 前端页面路径前缀
     */
    public static final String VIEW_PREFIX = "views/";

    public static final String LOGIN = "login";
    public static final String INDEX = "index";
    public static final String USER = VIEW_PREFIX + "system/user";
    public static final String USER_ADD = VIEW_PREFIX + "system/userAdd";
    public static final String ERROR_403 = VIEW_PREFIX + "error/403";
    public static final String ERROR_404 = VIEW_PREFIX + "error/404";
    public static final String ERROR_500 = VIEW_PREFIX + "error/500";

    public static final String CONSOLE = VIEW_PREFIX + "console/console";
    public static final String USER_DETAIL = VIEW_PREFIX + "system/userDetail";
    public static final String USER_UPDATE = VIEW_PREFIX + "system/userUpdate";
    public static final String ROLE = VIEW_PREFIX + "system/role";
    public static final String DEPT = VIEW_PREFIX + "system/dept";
}
