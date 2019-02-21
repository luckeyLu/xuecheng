package com.xuecheng.manage_cms.rabbitMq;

import com.xuecheng.framework.model.constants.CommonConstants;
import com.xuecheng.framework.utils.LoggerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 *  rabbitMq发送消息管理类
 * Created by lwenf on 2019-02-14.
 */
@Component
public class RabbitMqManage implements RabbitTemplate.ReturnCallback {
    private static final Logger MQ_PRODUER_LOGGER = LoggerFactory.getLogger(CommonConstants.MQ_PRODUER_LOGGER);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public RabbitMqManage(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
        rabbitTemplate.setReturnCallback(this);
        /**
         * 设置mandatory = true : 消息成功路由到队列，不会触发returnedMessage方法回调
         *                        消息未成功路由到队列，触发returnedMessage方法回调
         *  通过此设置结合returnedMessage方法可以确保消息是否路由到队列中
         */
        rabbitTemplate.setMandatory(true);
    }

    /**
     *  发送消息
     * @param exChanges
     * @param routkeys
     * @param context
     */
    public void sendMessage(String exChanges, String routkeys, String context){
        /**
         * 发送至交换机回调
         */
        this.rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack){
                /** 消息成功发送到交换机 */
                LoggerUtil.infoLog(MQ_PRODUER_LOGGER, "");
            }else {
                /** 消息发送失败 */
                // TODO
            }
        });
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        this.rabbitTemplate.convertAndSend(exChanges, routkeys, context, correlationData);
    }


    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        /** 消息发送交换机成功，但是未路由到队列 */
        // TODO
    }


    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
}
