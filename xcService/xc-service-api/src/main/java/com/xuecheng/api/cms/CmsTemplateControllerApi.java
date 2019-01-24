package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.response.CmsResult;
import com.xuecheng.framework.model.pagination.PaginationVo;

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
    public CmsResult<PaginationVo<CmsTemplate>> findAll();
}
