package com.xuecheng.manage_cms.repository.impl;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.manage_cms.dao.CmsPageDao;
import com.xuecheng.manage_cms.repository.CmsPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * cmsPage仓储实现
 * Created by lwenf on 2019-01-29.
 */
@Component
public class CmsPageRepositoryImpl implements CmsPageRepository {

    @Autowired
    private CmsPageDao cmsPageDao;

    @Override
    public CmsPage queryById(String pageId) {
        Optional<CmsPage> optional = cmsPageDao.findById(pageId);
        if (optional != null && optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    @Override
    public CmsPage innerOrUpdate(CmsPage cmsPage) {
        return cmsPageDao.save(cmsPage);
    }

    @Override
    public void delByPageId(String pageId) {
        cmsPageDao.deleteById(pageId);
    }

    @Override
    public CmsPage queryByPageNameAndPageWebPathAndSiteId(String pageName, String pageWebPath, String siteId) {
        return cmsPageDao.findByPageNameAndPageWebPathAndSiteId(pageName, pageWebPath, siteId);
    }

    @Override
    public Page<CmsPage> queryByPagination(Example<CmsPage> example, Pageable pageable) {
        return cmsPageDao.findAll(example, pageable);
    }

    public void setCmsPageDao(CmsPageDao cmsPageDao) {
        this.cmsPageDao = cmsPageDao;
    }
}
