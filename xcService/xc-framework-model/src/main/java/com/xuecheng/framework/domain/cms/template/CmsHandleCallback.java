package com.xuecheng.framework.domain.cms.template;

import com.xuecheng.framework.model.response.ResponseResult;

/**
 *  逻辑执行模板回调接口
 * Created by lwenf on 2019-01-23.
 */
public interface CmsHandleCallback<K> {

    /**
     *  入参校验
     */
    public void checkParams();

    /**
     *  执行核心计算或查询过程
     * @param <K>
     * @return
     */
    public <K extends ResponseResult>K doProcess();
}
