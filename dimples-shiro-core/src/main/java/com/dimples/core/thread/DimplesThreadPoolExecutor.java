package com.dimples.core.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/2/24
 */
@Slf4j
public class DimplesThreadPoolExecutor {

    private static DimplesThreadProperties threadProperties = new DimplesThreadProperties();

    private static ThreadPoolExecutor mExecutor;

    private DimplesThreadPoolExecutor() {
    }

    /**
     * 初始化ThreadPoolExecutor
     * 双重检查加锁,只有在第一次实例化的时候才启用同步机制,提高了性能
     */
    public static ThreadPoolExecutor initThreadPoolExecutor() {
        if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()) {
            synchronized (DimplesThreadPoolExecutor.class) {
                if (mExecutor == null || mExecutor.isShutdown() || mExecutor.isTerminated()) {
                    BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>();
                    ThreadFactory threadFactory = new DimplesThreadFactory();
                    RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();

                    mExecutor = new ThreadPoolExecutor(threadProperties.getCorePoolSize(),
                            threadProperties.getMaxPoolSize(),
                            threadProperties.getKeepAliveSeconds(),
                            threadProperties.getUnit(),
                            workQueue,
                            threadFactory,
                            handler);
                }
            }
        }
        return mExecutor;
    }



}
















