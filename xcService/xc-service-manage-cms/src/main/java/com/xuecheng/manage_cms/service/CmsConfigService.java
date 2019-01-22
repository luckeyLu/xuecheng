package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.response.CmsResult;
import com.xuecheng.framework.exception.CustomException;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.manage_cms.dao.CmsConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;


/**
 * Created by lwenf on 2019-01-03.
 */
@Service
public class CmsConfigService {

    @Autowired
    private CmsConfigRepository cmsConfigRepository;

    public CmsResult getmodel(String id){
        // 参数校验
        if (StringUtils.isEmpty(id)){
            throw new CustomException(CommonCode.INVALIDPARAM, "参数为空！");
        }
        Optional<CmsConfig> optional = cmsConfigRepository.findById(id);
        // 查询结果为空
        if (!optional.isPresent()){
            return new CmsResult<CmsConfig>(CommonCode.SUCCESS, null);
        }
        CmsConfig cmsConfig = optional.get();
        return new CmsResult<CmsConfig>(CommonCode.SUCCESS, cmsConfig);
    }


    public void setCmsConfigRepository(CmsConfigRepository cmsConfigRepository) {
        this.cmsConfigRepository = cmsConfigRepository;
    }


}
