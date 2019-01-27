package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsResult;
import com.xuecheng.framework.model.pagination.PaginationVo;

/**
 *  cms页面管理接口
 *
 * Created by lwenf on 2019-01-03.
 */
public interface CmsPageService {

    /**
     *  页面分页查询
     * @param page 当前页，默认从第1页开始
     * @param size 每页记录数
     * @param queryPageRequest
     * @return
     */
    public CmsResult<PaginationVo<CmsPage>> findList(int page, int size, final QueryPageRequest queryPageRequest);

    /**
     *  添加页面
     * @param cmsPage
     * @return
     */
    public CmsResult<CmsPage> add(CmsPage cmsPage);

    /**
     *  根据id查询页面
     * @return
     */
    public CmsResult<CmsPage> findById(String id);

    /**
     *  修改页面
     * @param id
     * @param cmsPage
     * @return
     */
    public CmsResult<CmsPage> edit(String id, CmsPage cmsPage);

    /**
     *  根据id删除数据
     * @param id
     * @return
     */
    public CmsResult del(String id);

    /**
     *  页面静态化
     * @param pageId
     * @return
     */
    public CmsResult<String> getPageHtml(String pageId);

    /**
     *  根据pageId发布页面到
     * @param pageId
     * @return
     */
    public CmsResult<Void> postPage(String pageId);

}
