package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsSiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lwenf on 2019-01-06.
 */
@Service
public class CmsSiteService {

    @Autowired
    private CmsSiteRepository cmsSiteRepository;

    public QueryResponseResult findAll(){
        List<CmsSite> all = cmsSiteRepository.findAll();

        QueryResult<CmsSite> queryResult = new  QueryResult();
        queryResult.setTotal(all.size());
        queryResult.setList(all);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }


    public void setCmsSiteRepository(CmsSiteRepository cmsSiteRepository) {
        this.cmsSiteRepository = cmsSiteRepository;
    }
}
