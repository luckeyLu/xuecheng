package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.response.CmsResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 *  Cmsc配置管理服务接口
 * Created by lwenf on 2019-01-13.
 */
@Api(value="cms配置管理接口",description="cms配置管理接口，提供查询数据模型的管理接口")
public interface CmsConfigControllerApi {

    @ApiOperation("根据id查询CMS配置信息")
    public CmsResult<CmsConfig> getModelById(String id);

}
