package com.dimples.core.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 创建一个线程
 * 定义生成线程的一些基础信息
 *
 * @author zhongyj <1126834403@qq.com><br/>
 * @date 2020/2/24
 */
public class DimplesThreadFactory implements ThreadFactory {

    private static final AtomicInteger POOL_SEQ = new AtomicInteger(1);

    private final AtomicInteger mThreadNum = new AtomicInteger(1);

    private final String mPrefix;

    private final boolean mDaemon;

    private final ThreadGroup mGroup;

    public DimplesThreadFactory() {
        this("DimplesPool", false);
    }

    public DimplesThreadFactory(String prefix) {
        this(prefix, false);
    }

    public DimplesThreadFactory(String prefix, boolean daemon) {
        mPrefix = "[ " + prefix + " ] - " + POOL_SEQ.getAndIncrement() + " - [ Thread ] - ";
        mDaemon = daemon;
        SecurityManager s = System.getSecurityManager();
        mGroup = (s == null) ? Thread.currentThread().getThreadGroup() : s.getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable r) {
        String name = mPrefix + mThreadNum.getAndIncrement();
        Thread ret = new Thread(mGroup, r, name, 0);
        if (ret.isDaemon()){
            ret.setDaemon(mDaemon);
        }
        if (ret.getPriority() != Thread.NORM_PRIORITY) {
            ret.setPriority(Thread.NORM_PRIORITY);
        }
        return ret;
    }

    public ThreadGroup getThreadGroup() {
        return mGroup;
    }

}
