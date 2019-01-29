package com.xuecheng.manage_cms_client.mq;

import com.alibaba.fastjson.JSON;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.model.constants.CommonConstants;
import com.xuecheng.framework.utils.LoggerUtil;
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
        LoggerUtil.infoLog(MQ_CONSUMER_LOGGER,"PageService.savePageToServerPath Received; [start]","MQ message", msg,
                "Exchange",RabbitmqConfig.EX_ROUTING_CMS_POSTPAGE, "Queue", RabbitmqConfig.QUEUE_CMS_POSTPAGE, "routingKey", routingKey);
        // 接收mq消息 消息格式为：{"pageId":"xxxx"}
        Map map = JSON.parseObject(msg, Map.class);
        String pageId = (String)map.get(PAGE_ID);
        // 校验接收到的消息
        if (StringUtils.isEmpty(pageId)){
            LoggerUtil.warnLog(MQ_CONSUMER_LOGGER, "Receive postpage msg, pageId is empty", "message", msg);
            return;
        }
        Optional<CmsPage> optional = cmsPageRepository.findById(pageId);
        if (!optional.isPresent()||optional.get()==null){
            LoggerUtil.warnLog(MQ_CONSUMER_LOGGER, "Query cmsPage by pageId is null", "pageId", pageId);
            return;
        }
        pageService.savePageToServerPath(optional.get());

        LoggerUtil.infoLog(MQ_CONSUMER_LOGGER,"PageService.savePageToServerPath consumer mq; [success]","MQ message", msg,
                "Exchange",RabbitmqConfig.EX_ROUTING_CMS_POSTPAGE, "Queue", RabbitmqConfig.QUEUE_CMS_POSTPAGE, "routingKey", routingKey);

    }

    public void setPageService(PageService pageService) {
        this.pageService = pageService;
    }

    public void setCmsPageRepository(CmsPageRepository cmsPageRepository) {
        this.cmsPageRepository = cmsPageRepository;
    }

}
