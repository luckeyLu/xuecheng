package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.response.CmsResult;
import com.xuecheng.framework.model.pagination.PaginationVo;
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
    public CmsResult<PaginationVo<CmsTemplate>> findAll(){
        List<CmsTemplate> all = cmsTemplateRepository.findAll();

        PaginationVo<CmsTemplate> paginationVo = new PaginationVo<>();
        paginationVo.setElements(all);
        paginationVo.setTotalRecords(all.size());

        return CmsResult.newSuccessResult(paginationVo);
    }


    public void setCmsTemplateRepository(CmsTemplateRepository cmsTemplateRepository) {
        this.cmsTemplateRepository = cmsTemplateRepository;
    }


}
