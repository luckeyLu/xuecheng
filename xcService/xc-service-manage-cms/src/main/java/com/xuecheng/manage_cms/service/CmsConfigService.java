package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.response.CmsResult;

/**
 *   Cmsc配置管理接口
 * Created by lwenf on 2019-01-03.
 */
public interface CmsConfigService {

    /**
     *  根据id cms管理配置
     * @param id
     * @return
     */
    public CmsResult<CmsConfig> getmodel(String id);

}
