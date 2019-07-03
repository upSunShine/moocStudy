package com.ext.springboot.springbootconsumer.consumer;

import com.ext.springboot.springproducer.entity.Order;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: xting
 * @CreateDate: 2019/6/24 15:22
 */
@Component
public class RabbitReciever {


    /**
     * spring.rabbitmq.listener.order.queue.name=exchange-queue
     spring.rabbitmq.listener.order.durable=true
     spring.rabbitmq.listener.order.exchange.name=exchange-1
     spring.rabbitmq.listener.order.exchange.durable=true
     spring.rabbitmq.listener.order.exchange.type=topic
     spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions=true
     spring.rabbitmq.listener.order.key = "springboot.#"
     * @param message
     * @param channel
     * @throws Exception
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${spring.rabbitmq.listener.order.queue.name}",
                    durable = "${pring.rabbitmq.listener.order.durable}"),
            exchange = @Exchange(value = "${ spring.rabbitmq.listener.order.exchange.name}",
                    durable = "true",type = "${spring.rabbitmq.listener.order.exchange.type}",
                    ignoreDeclarationExceptions = "${spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions}"),
            key = "${spring.rabbitmq.listener.order.key}"


    )
    )
    @RabbitHandler
    public void onOrderMessage(Message message, Channel channel) throws Exception{

            //消费方操作
            System.err.println("----收到消息,开始消费-------");
            System.err.println("消费端payload:"+message.getPayload());
            Long deliverTag = (Long)message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
            //ACK
            channel.basicAck(deliverTag,false);

    }
}
