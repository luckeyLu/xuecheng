package com.xuecheng.manage_cms_client.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by lwenf on 2019-01-02.
 */
public interface CmsPageRepository extends MongoRepository<CmsPage, String> {
}
