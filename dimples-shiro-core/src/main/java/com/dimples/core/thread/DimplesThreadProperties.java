package com.dimples.core.thread;

import java.util.concurrent.TimeUnit;

import lombok.Data;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/2/24
 */

@Data
public class DimplesThreadProperties {

    /**
     * 当前设备的CPU数量
     */
    public static int CPU_NUM = Runtime.getRuntime().availableProcessors();

    /**
     * 线程池的基本大小
     */
    private int corePoolSize = CPU_NUM * 2;

    /**
     * 线程池中允许的最大线程数
     */
    private int maxPoolSize = CPU_NUM * 10;


    /**
     * 线程池维护线程所允许的空闲时间，默认为60s
     */
    private int keepAliveSeconds = 60;

    /**
     *
     */
    private TimeUnit unit = TimeUnit.SECONDS;

    /**
     * 队列最大长度
     */
    private int queueCapacity = Integer.MAX_VALUE;

    /**
     * 允许核心线程超时
     */
    private boolean allowCoreThreadTimeout = false;

}

















