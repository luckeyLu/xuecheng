package com.xuecheng.manage_cms.repository.impl;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.manage_cms.dao.CmsTemplateDao;
import com.xuecheng.manage_cms.repository.CmsTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 *  cmsTempalte仓储实现
 * Created by lwenf on 2019-01-29.
 */
@Component
public class CmsTemplateRepositoryImpl implements CmsTemplateRepository {

    @Autowired
    private CmsTemplateDao cmsTemplateDao;

    @Override
    public List<CmsTemplate> queryAll() {
        return cmsTemplateDao.findAll();
    }

    @Override
    public CmsTemplate queryById(String id) {
        Optional<CmsTemplate> optional = cmsTemplateDao.findById(id);
        if (optional !=  null && optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    public void setCmsTemplateDao(CmsTemplateDao cmsTemplateDao) {
        this.cmsTemplateDao = cmsTemplateDao;
    }
}
