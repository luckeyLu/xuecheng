package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.response.CmsResult;
import com.xuecheng.framework.model.pagination.PaginationVo;
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
public class CmsSiteServiceTest {

    @Autowired
    private CmsSiteService cmsSiteService;

    @Test
    public void findAll(){
        CmsResult<PaginationVo<CmsSite>> all = cmsSiteService.findAll();
        System.out.println(all);
    }
}
