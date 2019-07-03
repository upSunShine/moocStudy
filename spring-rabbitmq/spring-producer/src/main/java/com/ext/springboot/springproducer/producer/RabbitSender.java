package com.ext.springboot.springproducer.producer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: xting
 * @CreateDate: 2019/6/24 11:00
 */
@Component
public class RabbitSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback(){

        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String s) {
            System.err.println("correlationData:"+correlationData);
            System.err.println("消息确认返回值:"+ack);
            if(!ack){
                System.err.println("异常处理。。。");
            }
        }
    };

    final RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
            System.err.println("return exchange:" + exchange + ",routingkey:" + routingKey
                    + ", replyCode: " + replyCode + ", replyText: " + replyText);
        }
    };


    public void send(Object message, Map<String,Object> properties){
        MessageHeaders headers = new MessageHeaders(properties);
        org.springframework.messaging.Message message1 = MessageBuilder.createMessage(message,headers);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId("123" + new Date().getTime());//id+时间戳 全局唯一
        rabbitTemplate.convertAndSend("exchange-1","springboot.ads",message1,correlationData);
    }

}
