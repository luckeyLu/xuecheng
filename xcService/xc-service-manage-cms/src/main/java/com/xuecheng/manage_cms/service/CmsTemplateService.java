package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.response.CmsResult;
import com.xuecheng.framework.model.pagination.PaginationVo;

/**
 *  cms模板管理接口
 * Created by lwenf on 2019-01-06.
 */
public interface CmsTemplateService {

    /**
     *  查询全部页面模板信息
     * @return
     */
    public CmsResult<PaginationVo<CmsTemplate>> findAll();

}
