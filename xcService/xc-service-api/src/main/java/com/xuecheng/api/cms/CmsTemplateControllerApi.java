package com.xuecheng.api.cms;

import com.xuecheng.framework.model.response.QueryResponseResult;

/**
 *  页面模板服务接口
 *
 * Created by lwenf on 2019-01-06.
 */
public interface CmsTemplateControllerApi {

    /**
     *  查询全部(不分页)
     * @return
     */
    public QueryResponseResult findAll();
}
