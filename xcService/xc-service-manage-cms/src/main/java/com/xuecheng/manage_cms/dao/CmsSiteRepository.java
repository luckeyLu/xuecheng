package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsSite;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by lwenf on 2019-01-06.
 */
public interface CmsSiteRepository extends MongoRepository<CmsSite, String> {
}
