package com.xuecheng.manage_cms.repository;

import com.xuecheng.framework.domain.system.SysDictionary;

/**
 *  系统字典仓储接口
 * Created by lwenf on 2019-02-23.
 */
public interface SysDictionaryRepository {

    public SysDictionary queryByType(String type);
}
