package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.response.CmsResult;
import com.xuecheng.framework.domain.system.SysDictionary;

/**
 *  系统字典管理服务层接口
 * Created by lwenf on 2019-02-23.
 */
public interface SysDictionaryService {

    /**
     *  根据类型查询系统数据字典
     * @param type
     * @return
     */
    public CmsResult<SysDictionary> queryByType(String type);
}
