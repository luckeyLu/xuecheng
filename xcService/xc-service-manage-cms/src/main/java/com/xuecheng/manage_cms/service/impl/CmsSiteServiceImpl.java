package com.xuecheng.manage_cms.service.impl;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.response.CmsResult;
import com.xuecheng.framework.domain.cms.template.CmsExecuteTemplate;
import com.xuecheng.framework.domain.cms.template.CmsHandleCallback;
import com.xuecheng.framework.model.pagination.PaginationVo;
import com.xuecheng.framework.utils.LoggerUtil;
import com.xuecheng.manage_cms.repository.CmsSitRepository;
import com.xuecheng.manage_cms.service.CmsSiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  站点管理接口实现类
 * Created by lwenf on 2019-01-25.
 */
@Service
public class CmsSiteServiceImpl implements CmsSiteService {

    @Autowired
    private CmsSitRepository cmsSiteRepository;

    public CmsResult<PaginationVo<CmsSite>> findAll(){
        return CmsExecuteTemplate.execute(new CmsHandleCallback<CmsResult<PaginationVo<CmsSite>>>() {
            @Override
            public String buildLog() {
                return LoggerUtil.buildParaLog("CmsSiteService.findAll");
            }

            @Override
            public void checkParams() {

            }

            @Override
            public CmsResult<PaginationVo<CmsSite>> doProcess() {

                List<CmsSite> all = cmsSiteRepository.queryAll();

                PaginationVo<CmsSite> paginationVo = new PaginationVo<>();
                paginationVo.setElements(all);
                paginationVo.setTotalRecords(all.size());

                return CmsResult.newSuccessResult(paginationVo);
            }
        });
    }

    public void setCmsSiteRepository(CmsSitRepository cmsSiteRepository) {
        this.cmsSiteRepository = cmsSiteRepository;
    }
}
