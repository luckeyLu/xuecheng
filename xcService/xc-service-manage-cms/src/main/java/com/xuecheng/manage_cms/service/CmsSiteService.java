package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.response.CmsResult;
import com.xuecheng.framework.model.pagination.PaginationVo;
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

    public CmsResult<PaginationVo<CmsSite>> findAll(){
        List<CmsSite> all = cmsSiteRepository.findAll();

        PaginationVo<CmsSite> paginationVo = new PaginationVo<>();
        paginationVo.setElements(all);
        paginationVo.setTotalRecords(all.size());

        return CmsResult.newSuccessResult(paginationVo);
    }


    public void setCmsSiteRepository(CmsSiteRepository cmsSiteRepository) {
        this.cmsSiteRepository = cmsSiteRepository;
    }
}
