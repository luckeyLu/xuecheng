package com.xuecheng.manage_cms.repository;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 *  cmsPage仓储
 * Created by lwenf on 2019-01-29.
 */
public interface CmsPageRepository {

    /**
     *  通过pageId查询单条记录
     * @param pageId
     * @return
     */
    public CmsPage queryById(String pageId);

    /**
     *  保存单条记录
     * @param cmsPage
     * @return
     */
    public CmsPage innerOrUpdate(CmsPage cmsPage);

    /**
     *  根据pageId删除单条记录
     * @param pageId
     */
    public void delByPageId(String pageId);

    /**
     *  根据(pageName+pageWebPath+site)唯一主键查询单条记录
     * @param pageName
     * @param pageWebPath
     * @param siteId
     * @return
     */
    public CmsPage queryByPageNameAndPageWebPathAndSiteId(String pageName, String pageWebPath, String siteId);

    /**
     *  分页条件查询
     * @param example
     * @param pageable
     * @return
     */
    public Page<CmsPage> queryByPagination(Example<CmsPage> example, Pageable pageable);

}
