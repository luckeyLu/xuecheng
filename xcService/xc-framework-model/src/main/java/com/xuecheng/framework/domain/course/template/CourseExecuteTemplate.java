package com.xuecheng.framework.domain.course.template;

import com.xuecheng.framework.domain.course.response.CourseResult;
import com.xuecheng.framework.model.constants.CommonConstants;
import com.xuecheng.framework.utils.LoggerUtil;
import com.xuecheng.framework.utils.ThreadLocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lwenf on 2019-02-14.
 */
public class CourseExecuteTemplate {
    private static final Logger COURSE_EXCUTE_LOGGER = LoggerFactory.getLogger(CommonConstants.COMMON_EXCUTE_LOGGER);

    public static <T, K extends CourseResult<T>> CourseResult<T> execute(CourseHandleCallback<K> callback){

        // 打印日志
        String bizLog = callback.buildLog();

        LoggerUtil.infoLog(COURSE_EXCUTE_LOGGER, bizLog+" [Start]");

        //参数校验
        callback.checkParams();

        long startTiems = System.currentTimeMillis();
        //核心业务逻辑处理
        K obj = callback.doProcess();

        LoggerUtil.infoLog(COURSE_EXCUTE_LOGGER,
                bizLog+" [End]("+obj.isSuccess()+")"+" [Time-consuming] : "+(System.currentTimeMillis()-startTiems)+"ms");
        // 清除本地线程变量
        ThreadLocalUtil.clearRef();
        return obj;
    }
}
