package com.xuecheng.framework.domain.cms.template;

import com.xuecheng.framework.domain.cms.response.CmsResult;
import com.xuecheng.framework.model.constants.CommonConstants;
import com.xuecheng.framework.utils.LoggerUtil;
import com.xuecheng.framework.utils.ThreadLocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  cms执行逻辑模板
 * Created by lwenf on 2019-01-18.
 */
public class CmsExecuteTemplate {
    private static final Logger CMS_EXCUTE_LOGGER = LoggerFactory.getLogger(CommonConstants.CMS_EXCUTE_LOGGER);

    public static <T, K extends CmsResult<T>> CmsResult<T> execute(CmsHandleCallback<K> callback){

        // 打印日志
        String bizLog = callback.buildLog();

        LoggerUtil.infoLog(CMS_EXCUTE_LOGGER, bizLog+" [Start]");

        //参数校验
        callback.checkParams();

        long startTiems = System.currentTimeMillis();
        //核心业务逻辑处理
        K obj = callback.doProcess();

        LoggerUtil.infoLog(CMS_EXCUTE_LOGGER,
                bizLog+" [End]("+obj.isSuccess()+")"+" [Time-consuming] : "+(System.currentTimeMillis()-startTiems)+"ms");
        // 清除本地线程变量
        ThreadLocalUtil.clearRef();
        return obj;
    }
}
