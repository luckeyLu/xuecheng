package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.response.CmsResult;
import com.xuecheng.framework.model.pagination.PaginationVo;

/**
 *  网页站点服务接口
 * Created by lwenf on 2019-01-06.
 */

public interface CmsSiteControllerApi {

    /**
     *  查询全部站点信息(不分页)
     * @return
     */
    public CmsResult<PaginationVo<CmsSite>> findALL();
}
