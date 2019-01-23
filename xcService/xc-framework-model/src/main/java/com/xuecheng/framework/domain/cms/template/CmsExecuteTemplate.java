package com.xuecheng.framework.domain.cms.template;

import com.xuecheng.framework.domain.cms.response.CmsResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  cms执行逻辑模板
 * Created by lwenf on 2019-01-18.
 */
public class CmsExecuteTemplate {
    private static final Logger LOGGER = LoggerFactory.getLogger(CmsExecuteTemplate.class);

    public static <T, K extends CmsResult<T>> CmsResult<T> execute(CmsHandleCallback<K> callback){
        //参数校验
        callback.checkParams();

        long start = System.currentTimeMillis();
        //核心业务逻辑处理
        K obj = callback.doProcess();
        long end = System.currentTimeMillis();
        LOGGER.info("执行业务逻辑时间："+(end-start));
        return obj;
    }
}
