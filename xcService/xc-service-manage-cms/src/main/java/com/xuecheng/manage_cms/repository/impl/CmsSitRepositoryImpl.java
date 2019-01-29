package com.xuecheng.manage_cms.repository.impl;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.manage_cms.dao.CmsSiteDao;
import com.xuecheng.manage_cms.repository.CmsSitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * cmsSite仓储实现
 * Created by lwenf on 2019-01-29.
 */
@Component
public class CmsSitRepositoryImpl implements CmsSitRepository{

    @Autowired
    private CmsSiteDao cmsSiteDao;

    @Override
    public List<CmsSite> queryAll() {
        return cmsSiteDao.findAll();
    }

    public void setCmsSiteDao(CmsSiteDao cmsSiteDao) {
        this.cmsSiteDao = cmsSiteDao;
    }
}
