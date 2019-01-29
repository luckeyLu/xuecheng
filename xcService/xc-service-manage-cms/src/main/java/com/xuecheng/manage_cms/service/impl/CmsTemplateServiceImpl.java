package com.xuecheng.manage_cms.service.impl;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.response.CmsResult;
import com.xuecheng.framework.domain.cms.template.CmsExecuteTemplate;
import com.xuecheng.framework.domain.cms.template.CmsHandleCallback;
import com.xuecheng.framework.model.pagination.PaginationVo;
import com.xuecheng.framework.utils.LoggerUtil;
import com.xuecheng.manage_cms.repository.CmsTemplateRepository;
import com.xuecheng.manage_cms.service.CmsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  cms模板管理接口实现类
 * Created by lwenf on 2019-01-25.
 */
@Service
public class CmsTemplateServiceImpl implements CmsTemplateService {

    @Autowired
    private CmsTemplateRepository cmsTemplateRepository;

    /**
     *  查询全部页面模板信息
     * @return
     */
    public CmsResult<PaginationVo<CmsTemplate>> findAll(){
        return CmsExecuteTemplate.execute(new CmsHandleCallback<CmsResult<PaginationVo<CmsTemplate>>>() {
            @Override
            public String buildLog() {
                return LoggerUtil.buildParaLog("CmsTemplateService.findAll");
            }

            @Override
            public void checkParams() {

            }

            @Override
            public CmsResult<PaginationVo<CmsTemplate>> doProcess() {
                List<CmsTemplate> all = cmsTemplateRepository.queryAll();

                PaginationVo<CmsTemplate> paginationVo = new PaginationVo<>();
                paginationVo.setElements(all);
                paginationVo.setTotalRecords(all.size());

                return CmsResult.newSuccessResult(paginationVo);
            }
        });
    }


    public void setCmsTemplateRepository(CmsTemplateRepository cmsTemplateRepository) {
        this.cmsTemplateRepository = cmsTemplateRepository;
    }
}
