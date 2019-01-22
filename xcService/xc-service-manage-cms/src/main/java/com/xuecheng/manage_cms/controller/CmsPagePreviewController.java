package com.xuecheng.manage_cms.controller;

import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsResult;
import com.xuecheng.framework.exception.CustomException;
import com.xuecheng.framework.web.BaseController;
import com.xuecheng.manage_cms.service.CmsPageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

/**
 * Created by lwenf on 2019-01-19.
 */
@Controller
@RequestMapping("/cms")
public class CmsPagePreviewController extends BaseController {

    @Autowired
    private CmsPageService cmsPageService;

    @RequestMapping(value = "/preview/{pageId}", method = RequestMethod.GET)
    public void pagePreview(@PathVariable("pageId") String pageId){
        CmsResult<String> pageHtml = cmsPageService.getPageHtml(pageId);
        if (!pageHtml.isSuccess() || StringUtils.isEmpty(pageHtml.getResultData())){
            throw new CustomException(CmsCode.CMS_PAGE_FREEMAEKRERROR);
        }
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(pageHtml.getResultData().getBytes("utf-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCmsPageService(CmsPageService cmsPageService) {
        this.cmsPageService = cmsPageService;
    }
}
