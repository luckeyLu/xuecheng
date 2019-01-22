package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lwenf on 2019-01-06.
 */
@Service
public class CmsTemplateService {

    @Autowired
    private CmsTemplateRepository cmsTemplateRepository;

    /**
     *  查询全部页面模板信息
     * @return
     */
    public QueryResponseResult findAll(){
        List<CmsTemplate> all = cmsTemplateRepository.findAll();

        QueryResult<CmsTemplate> queryResult = new QueryResult();
        queryResult.setTotal(all.size());
        queryResult.setList(all);
        return new QueryResponseResult(CommonCode.SUCCESS, queryResult);
    }


    public void setCmsTemplateRepository(CmsTemplateRepository cmsTemplateRepository) {
        this.cmsTemplateRepository = cmsTemplateRepository;
    }


}
