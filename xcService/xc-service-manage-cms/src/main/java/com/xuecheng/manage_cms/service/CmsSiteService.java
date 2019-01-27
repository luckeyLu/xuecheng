package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.response.CmsResult;
import com.xuecheng.framework.model.pagination.PaginationVo;

/**
 *  站点管理接口
 * Created by lwenf on 2019-01-06.
 */
public interface CmsSiteService {

    /**
     *  查询所有站点信息
     * @return
     */
    public CmsResult<PaginationVo<CmsSite>> findAll();

}
