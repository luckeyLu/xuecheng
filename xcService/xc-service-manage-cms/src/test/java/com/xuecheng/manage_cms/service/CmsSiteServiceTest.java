package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.model.response.QueryResponseResult;
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
        QueryResponseResult all = cmsSiteService.findAll();
        System.out.println(all.getQueryResult().getList());
    }
}
