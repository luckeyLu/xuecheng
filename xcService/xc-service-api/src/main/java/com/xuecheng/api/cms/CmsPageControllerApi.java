package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * Created by lwenf on 2019-01-01.
 */
@Api(value="cms页面管理接口",description="cms页面管理接口，提供页面的增、删、改、查")
public interface CmsPageControllerApi {

    /**
     *   页面查询
     *
     * @param page
     * @param size
     * @param queryPageRequest
     * @return
     */
    @ApiOperation("分页查询页面列表")
    @ApiImplicitParams({@ApiImplicitParam(name="page",value="页码",required=true,paramType="path",dataType="int"),
            @ApiImplicitParam(name="size",value="每页记录 数",required=true,paramType="path",dataType="int")})
    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest);

    /**
     *  添加页面
     * @param cmsPage
     * @return
     */
    @ApiOperation("添加页面")
    public CmsPageResult add(CmsPage cmsPage);

    /**
     * 根据id查询页面信息
     * @return
     */
    @ApiOperation("根据Id查询页面")
    public CmsPageResult findById(String id);

    /**
     *  更新页面信息
     * @param id
     * @param cmsPage
     * @return
     */
    @ApiOperation("更新页面")
    public CmsPageResult edit(String id, CmsPage cmsPage);

    /**
     *  根据id删除页面信息
     * @param id
     * @return
     */
    public ResponseResult del(String id);
}
