package com.dimples.boot.core.eunm;

/**
 * 记录日志操作类型
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2019/11/8
 */
public enum OpsLogType {
    /**
     * 添加
     */
    ADD,

    /**
     * 修改
     */
    UPDATE,

    /**
     * 删除
     */
    DELETE,

    /**
     * 查询
     */
    SELECT,

    /**
     * 设置
     */
    SET,

    /**
     * 重置
     */
    RESET,

    /**
     * 停用
     */
    STOP,

    /**
     * 上传
     */
    UPLOAD,

    /**
     * 下载
     */
    DOWNLOAD;

}
