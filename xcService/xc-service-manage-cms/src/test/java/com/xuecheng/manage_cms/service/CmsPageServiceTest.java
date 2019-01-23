package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by lwenf on 2019-01-06.
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageServiceTest {

    @Autowired
    private CmsPageService cmsPageService;

    @Test
    public void addTest(){
        CmsPage cmsPage = new CmsPage();
        cmsPage.setPageName("测试名称");
        cmsPage.setPageAliase("测试别名");
        cmsPage.setSiteId("001");
        cmsPage.setTemplateId("001");
        cmsPage.setPageWebPath("test");

        CmsResult<CmsPage> addCmsPage = cmsPageService.add(cmsPage);
        System.out.println(addCmsPage);
    }

    @Test
    public void getPageHtml(){
        CmsResult<String> pageHtml = cmsPageService.getPageHtml("5c34b31a34f69b4310bb85f9");
        System.out.println(pageHtml.getResultData());
    }
}
