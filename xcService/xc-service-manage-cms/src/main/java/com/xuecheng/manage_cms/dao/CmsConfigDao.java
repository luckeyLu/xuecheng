package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by lwenf on 2019-01-02.
 */
public interface CmsConfigDao extends MongoRepository<CmsConfig, String> {
}
