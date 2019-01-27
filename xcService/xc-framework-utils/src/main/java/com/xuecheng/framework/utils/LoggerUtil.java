package com.xuecheng.framework.utils;


import com.xuecheng.framework.model.constants.CommonConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static javax.swing.UIManager.getString;

/**
 *  日志工具类
 *
 * Created by lwenf on 2019-01-25.
 */
public class LoggerUtil {
    private static final Logger COMMON_ERROR_LOGGER = LoggerFactory.getLogger(CommonConstants.COMMON_ERROR_LOGGER);

    public static void buildInfoLog(Logger logger, String msg){
        logger.info(msg);
    }

    public static void buildError(Logger logger, String msg){
        logger.error(msg);
    }

    public static void buildErrorLog(Logger logger, String msg){
        logger.error(msg);
        COMMON_ERROR_LOGGER.error(msg);
    }

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
                builder.append(getString(strings[i << 1]));
                builder.append("=");
                builder.append(getString(strings[(i << 1) + 1]));
                builder.append(",");
            }
            builder.deleteCharAt(builder.length() - 1);
            builder.append(")");
        }
        return builder.toString();
    }
}
