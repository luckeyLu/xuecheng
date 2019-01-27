package com.xuecheng.manage_cms.service.impl;

import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.response.CmsResult;
import com.xuecheng.framework.domain.cms.template.CmsExecuteTemplate;
import com.xuecheng.framework.domain.cms.template.CmsHandleCallback;
import com.xuecheng.framework.exception.CustomException;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.utils.LoggerUtil;
import com.xuecheng.manage_cms.dao.CmsConfigRepository;
import com.xuecheng.manage_cms.service.CmsConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 *  Cmsc配置管理接口实现类
 *
 * Created by lwenf on 2019-01-25.
 */
@Service
public class CmsConfigServiceImpl implements CmsConfigService {
    @Autowired
    private CmsConfigRepository cmsConfigRepository;

    public CmsResult<CmsConfig> getmodel(String id){
        return CmsExecuteTemplate.execute(new CmsHandleCallback<CmsResult<CmsConfig>>() {
            @Override
            public String buildLog() {
                return LoggerUtil.buildParaLog("CmsConfigService.getmodel","id", id);
            }

            @Override
            public void checkParams() {
                // 参数校验
                if (StringUtils.isEmpty(id)) {
                    throw new CustomException(CommonCode.INVALIDPARAM, "参数为空！");
                }
            }

            @Override
            public CmsResult<CmsConfig> doProcess() {

                Optional<CmsConfig> optional = cmsConfigRepository.findById(id);
                // 查询结果为空
                if (!optional.isPresent()) {
                    return CmsResult.newFailResult("查询结果为空！");
                }
                CmsConfig cmsConfig = optional.get();
                return CmsResult.newSuccessResult(cmsConfig);
            }
        });
    }


    public void setCmsConfigRepository(CmsConfigRepository cmsConfigRepository) {
        this.cmsConfigRepository = cmsConfigRepository;
    }
}
