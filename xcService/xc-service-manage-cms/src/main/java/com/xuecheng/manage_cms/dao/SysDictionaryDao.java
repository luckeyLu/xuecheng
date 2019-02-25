package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by lwenf on 2019-01-06.
 */
public interface SysDictionaryDao extends MongoRepository<SysDictionary, String> {

    public SysDictionary findByAndDType(String type);
}
