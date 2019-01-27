package com.xuecheng.manage_cms_client.service;

import com.xuecheng.framework.domain.cms.response.CmsResult;

/**
 *  页面发布接口
 * Created by lwenf on 2019-01-21.
 */
public interface PageService {

    /**
     *  将页面html保存到服务器页面物理路径下
     * @param pageId
     * @return
     */
    public CmsResult<String> savePageToServerPath(String pageId);

}
