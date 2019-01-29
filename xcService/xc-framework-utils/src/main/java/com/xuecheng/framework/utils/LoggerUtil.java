package com.xuecheng.framework.utils;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

/**
 *  日志工具类
 *
 * Created by lwenf on 2019-01-25.
 */
public class LoggerUtil {

    /**
     *  info日志
     * @param logger
     * @param message
     */
    public static void infoLog(Logger logger, String message){
        if (logger.isInfoEnabled()){
            logger.info(getBizNo()+","+message);
        }
    }

    /**
     *  info日志带信息
     * @param logger
     * @param message
     * @param strings
     */
    public static void infoLog(Logger logger, String message, Object... strings){
        if (logger.isInfoEnabled()){
            logger.info(getBizNo()+","+message + buildParamPairString(strings));
        }
    }

    /**
     *  告警日志
     * @param logger
     * @param message
     */
    public static void warnLog(Logger logger, String message){
        logger.warn(getBizNo()+","+message);
    }

    /**
     *  告警日志细信息
     * @param logger
     * @param message
     * @param strings
     */
    public static void warnLog(Logger logger, String message, Object... strings){
        logger.warn(getBizNo()+","+message + buildParamPairString(strings));
    }

    /**
     * 错误日志
     * @param logger
     * @param message
     */
    public static void errorLog(Logger logger, String message){
        logger.error(getBizNo()+","+message);
    }

    /**
     *  错误日志细信息
     * @param logger
     * @param message
     * @param strings
     */
    public static void errorLog(Logger logger, String message, Object... strings){
        logger.error(getBizNo()+","+message + buildParamPairString(strings));
    }

    /**
     *  错误日志带信息&&错误详细
     * @param logger
     * @param t
     * @param message
     * @param strings
     */
    public static void errorLog(Logger logger,Throwable t, String message, Object... strings){
        logger.error(getBizNo()+","+message + buildParamPairString(strings), t);
    }

    /**
     *  构建参数 日志结果：[类名.方法名](参数名=参数值，参数名=参数值...)
     * @param methodName
     * @param strings
     * @return
     */
    public static String buildParaLog(String methodName, Object... strings) {

        StringBuilder builder = new StringBuilder();
        builder.append("[");
        if (StringUtils.isNotBlank(methodName)) {
            builder.append(methodName);
        }
        builder.append("]");
        builder.append(buildParamPairString(strings));
        return builder.toString();
    }

    public static String buildParamPairString(Object... strings) {

        StringBuilder builder = new StringBuilder();
        if (strings != null && strings.length >= 2) {
            builder.append("(");
            for (int i = 0; i < (strings.length >> 1); i++) {
                if (strings[i << 1] != null){
                    builder.append((strings[i << 1]).toString());
                }else {
                    builder.append(" ");
                }
                builder.append("=");
                if (strings[(i << 1) + 1] != null){
                    builder.append((strings[(i << 1) + 1]).toString());
                }else {
                    builder.append(" ");
                }
                builder.append(",");
            }
            builder.deleteCharAt(builder.length() - 1);
            builder.append(")");
        }
        return builder.toString();
    }

    /**
     *  获得本地线程上下文业务流水号，做为全局唯一id
     * @return
     */
    public static String getBizNo(){
        return ThreadLocalUtil.getBizNo();
    }
}
