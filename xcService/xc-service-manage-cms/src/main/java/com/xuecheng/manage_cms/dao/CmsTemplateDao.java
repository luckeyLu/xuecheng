package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by lwenf on 2019-01-06.
 */
public interface CmsTemplateDao extends MongoRepository<CmsTemplate, String> {
}
