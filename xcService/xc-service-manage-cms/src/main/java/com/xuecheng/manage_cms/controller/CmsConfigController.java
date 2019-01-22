package com.xuecheng.manage_cms.controller;

import com.xuecheng.api.cms.CmsConfigControllerApi;
import com.xuecheng.framework.domain.cms.CmsConfig;
import com.xuecheng.framework.domain.cms.response.CmsResult;
import com.xuecheng.manage_cms.service.CmsConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lwenf on 2019-01-18.
 */
@RestController()
@RequestMapping("/cms/config")
public class CmsConfigController implements CmsConfigControllerApi {

    @Autowired
    private CmsConfigService cmsConfigService;

    @Override
    @GetMapping("/getmodel/{id}")
    public CmsResult<CmsConfig> getModelById(@PathVariable String id) {
        return cmsConfigService.getmodel(id);
    }

    public void setCmsConfigService(CmsConfigService cmsConfigService) {
        this.cmsConfigService = cmsConfigService;
    }
}
