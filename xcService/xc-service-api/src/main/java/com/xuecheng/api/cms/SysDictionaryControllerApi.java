package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.response.CmsResult;
import com.xuecheng.framework.domain.system.SysDictionary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 *  系统数据字典api接口
 * Created by lwenf on 2019-02-23.
 */
@Api(value="系统数据字典服务接口",description="系统数据字典管理接口，提供系统数据字典的增、删、改、查")
public interface SysDictionaryControllerApi {

    @ApiOperation("根据数据字典类型查询数据字典")
    public CmsResult<SysDictionary> querySysDictionaryByType(String type);

}
