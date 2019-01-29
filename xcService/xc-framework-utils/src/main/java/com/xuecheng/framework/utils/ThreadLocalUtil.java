package com.xuecheng.framework.utils;

import com.xuecheng.framework.utils.model.ThreadLocalContext;

import java.util.UUID;

/**
 *  业务本地线程上线文工具类
 * Created by lwenf on 2019-01-29.
 */
public class ThreadLocalUtil {

    /** 本地业务线程 */
    private static final ThreadLocal<ThreadLocalContext> LOCAL_CONTEXT = new ThreadLocal<ThreadLocalContext>();

    /**
     * 获取方法
     * @return
     */
    public static ThreadLocalContext get() {
        return getThreadLocalContext();
    }

    /**
     * 获取业务流水号
     *
     * @return
     */
    public static String getBizNo() {
        ThreadLocalContext threadContext = getThreadLocalContext();
        return threadContext.getBizNo();
    }

    /**
     * 设置业务流水号
     *
     * @return
     */
    public static void setBizNo(String bizNO) {
        ThreadLocalContext threadContext = getThreadLocalContext();
        threadContext.setBizNo(bizNO);
    }

    /**
     * 生成业务流水号 <br/>
     * 用于贯穿一次线程上下文
     *
     * @return
     */
    public static String genBizNo() {

        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    /**
     * 清空线程变量
     */
    public static void clearRef() {
        LOCAL_CONTEXT.remove();
    }


    /**
     * 获得线程变量
     * @return
     */
    private static ThreadLocalContext getThreadLocalContext() {
        ThreadLocalContext threadContext = LOCAL_CONTEXT.get();
        if (threadContext == null) {
            threadContext = new ThreadLocalContext();
            threadContext.setBizNo(genBizNo());
            LOCAL_CONTEXT.set(threadContext);
        }
        return threadContext;
    }
}
