package com.xuecheng.manage_cms.repository;

import com.xuecheng.framework.domain.cms.CmsConfig;

/**
 * cmsConfig仓储
 * Created by lwenf on 2019-01-29.
 */
public interface CmsConfigRepository {

    /**
     *  根据id查询单条记录
     * @param id
     * @return
     */
    public CmsConfig queryById(String id);
}
