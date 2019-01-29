package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by lwenf on 2019-01-02.
 */
public interface CmsPageDao extends MongoRepository<CmsPage, String> {

    /**
     *  根据唯一索引查询
     * @param pageName
     * @param pageWebPath
     * @param siteId
     * @return
     */
    public CmsPage findByPageNameAndPageWebPathAndSiteId(String pageName, String pageWebPath, String siteId);

}
