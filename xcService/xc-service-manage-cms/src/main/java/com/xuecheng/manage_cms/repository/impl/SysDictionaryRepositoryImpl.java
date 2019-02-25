package com.xuecheng.manage_cms.repository.impl;

import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_cms.dao.SysDictionaryDao;
import com.xuecheng.manage_cms.repository.SysDictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *  系统字典仓储接口实现
 * Created by lwenf on 2019-02-23.
 */
@Component
public class SysDictionaryRepositoryImpl implements SysDictionaryRepository {

    @Autowired
    private SysDictionaryDao sysDictionaryDao;

    @Override
    public SysDictionary queryByType(String type) {
        return sysDictionaryDao.findByAndDType(type);
    }
}
