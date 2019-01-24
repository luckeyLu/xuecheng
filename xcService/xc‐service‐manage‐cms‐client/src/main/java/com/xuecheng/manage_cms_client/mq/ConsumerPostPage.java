package com.xuecheng.manage_cms_client.mq;

import com.alibaba.fastjson.JSON;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsResult;
import com.xuecheng.manage_cms_client.dao.CmsPageRepository;
import com.xuecheng.manage_cms_client.service.PageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 *  mq监听类， 接收页面发布消息
 * Created by lwenf on 2019-01-21.
 */
@Component
public class ConsumerPostPage {
    private static  final Logger LOGGER = LoggerFactory.getLogger(ConsumerPostPage.class);

    private static final String PAGE_ID = "pageId";

    @Autowired
    private PageService pageService;

    @Autowired
    private CmsPageRepository cmsPageRepository;

    @RabbitListener(queues = {"${xuecheng.mq.queue}"})
    public void postPage(String msg){
        LOGGER.info("receive postpage RabbitMQ msg = {}",msg);
        // 接收mq消息 消息格式为：{"pageId":"xxxx"}
        Map map = JSON.parseObject(msg, Map.class);
        String pageId = (String)map.get(PAGE_ID);
        // 校验接收到的消息
        if (StringUtils.isEmpty(pageId)){
            LOGGER.error("receive postpage msg,cmsPage is null,pageId:{}",pageId);
            return;
        }
        Optional<CmsPage> optional = cmsPageRepository.findById(pageId);
        if (!optional.isPresent()||optional.get()==null){
            LOGGER.error("query cmsPage by pageId is null,pageId:{}",pageId);
            return;
        }
        CmsResult<Void> result = pageService.savePageToServerPath(pageId);
    }

    public void setPageService(PageService pageService) {
        this.pageService = pageService;
    }

    public void setCmsPageRepository(CmsPageRepository cmsPageRepository) {
        this.cmsPageRepository = cmsPageRepository;
    }

}
