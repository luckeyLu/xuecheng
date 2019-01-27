package com.xuecheng.manage_cms_client.mq;

import com.alibaba.fastjson.JSON;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsResult;
import com.xuecheng.framework.model.constants.CommonConstants;
import com.xuecheng.manage_cms_client.config.RabbitmqConfig;
import com.xuecheng.manage_cms_client.dao.CmsPageRepository;
import com.xuecheng.manage_cms_client.service.PageService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 *  mq监听类， 接收页面发布消息
 * Created by lwenf on 2019-01-21.
 */
@Component
public class ConsumerPostPage {
    private static  final Logger MQ_CONSUMER_LOGGER = LoggerFactory.getLogger(CommonConstants.MQ_CONSUMER_LOGGER);

    private static final String PAGE_ID = "pageId";

    //routingKey 即站点Id
    @Value("${xuecheng.mq.routingKey}")
    public  String routingKey;

    @Autowired
    private PageService pageService;

    @Autowired
    private CmsPageRepository cmsPageRepository;

    @RabbitListener(queues = {"${xuecheng.mq.queue}"})
    public void postPage(String msg){
        MQ_CONSUMER_LOGGER.info("PageService.savePageToServerPath start consumer mq; [Exchange={},Queue={},routingKey={}, msg={}",
                RabbitmqConfig.EX_ROUTING_CMS_POSTPAGE, RabbitmqConfig.QUEUE_CMS_POSTPAGE, routingKey, msg);
        // 接收mq消息 消息格式为：{"pageId":"xxxx"}
        Map map = JSON.parseObject(msg, Map.class);
        String pageId = (String)map.get(PAGE_ID);
        // 校验接收到的消息
        if (StringUtils.isEmpty(pageId)){
            MQ_CONSUMER_LOGGER.error("receive postpage msg,cmsPage is null,pageId:{}",pageId);
            return;
        }
        Optional<CmsPage> optional = cmsPageRepository.findById(pageId);
        if (!optional.isPresent()||optional.get()==null){
            MQ_CONSUMER_LOGGER.error("query cmsPage by pageId is null,pageId:{}",pageId);
            return;
        }
        CmsResult<String> result = pageService.savePageToServerPath(pageId);

        MQ_CONSUMER_LOGGER.info("PageService.savePageToServerPath consumer mq success; [Exchange={},Queue={},routingKey={}, msg={},pageHtml={}]",
                RabbitmqConfig.EX_ROUTING_CMS_POSTPAGE, RabbitmqConfig.QUEUE_CMS_POSTPAGE, routingKey, msg, result.getResultData());
    }

    public void setPageService(PageService pageService) {
        this.pageService = pageService;
    }

    public void setCmsPageRepository(CmsPageRepository cmsPageRepository) {
        this.cmsPageRepository = cmsPageRepository;
    }

}
