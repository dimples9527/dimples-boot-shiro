package com.dimples.core.constant;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/1/21
 */
public class DimplesConstant {

    /**
     * 注册用户角色ID
     */
    public static final Long REGISTER_ROLE_ID = 3L;

    /**
     * 排序规则：降序
     */
    public static final String ORDER_DESC = "desc";
    /**
     * 排序规则：升序
     */
    public static final String ORDER_ASC = "asc";

    /**
     * 验证码 Session Key
     */
    public static final String CODE_PREFIX = "dimples_captcha_";

    /**
     * 允许下载的文件类型，根据需求自己添加（小写）
     */
    public static final String[] VALID_FILE_TYPE = {"xlsx", "zip"};

    /**
     * getDataTable 中 HashMap 默认的初始化容量
     */
    public static final int DATA_MAP_INITIAL_CAPACITY = 4;
    /**
     * 异步线程池名称
     */
    public static final String ASYNC_POOL = "dimplesAsyncThreadPool";

}
