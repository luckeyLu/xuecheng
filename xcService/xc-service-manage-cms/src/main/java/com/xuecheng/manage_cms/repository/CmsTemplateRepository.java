package com.xuecheng.manage_cms.repository;

import com.xuecheng.framework.domain.cms.CmsTemplate;

import java.util.List;

/**
 *  cmsTempalte仓储
 * Created by lwenf on 2019-01-29.
 */
public interface CmsTemplateRepository {

    /**
     *  查询全部记录
     * @return
     */
    public List<CmsTemplate> queryAll();

    /**
     *  根据id查询单条记录
     * @param Id
     * @return
     */
    public CmsTemplate queryById(String Id);
}
