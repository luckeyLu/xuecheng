package com.xuecheng.manage_cms.repository.impl;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.manage_cms.dao.CmsConfigDao;
import com.xuecheng.manage_cms.repository.CmsConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * cmsConfig仓储实现
 * Created by lwenf on 2019-01-29.
 */
@Component
public class CmsConfigRepositoryImpl implements CmsConfigRepository {

    @Autowired
    private CmsConfigDao cmsConfigDao;

    @Override
    public CmsConfig queryById(String id) {
        Optional<CmsConfig> optional = cmsConfigDao.findById(id);
        if (optional != null && optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    public void setCmsConfigDao(CmsConfigDao cmsConfigDao) {
        this.cmsConfigDao = cmsConfigDao;
    }
}
