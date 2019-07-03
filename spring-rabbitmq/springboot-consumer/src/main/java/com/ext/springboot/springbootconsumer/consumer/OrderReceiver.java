package com.ext.springboot.springbootconsumer.consumer;

import com.ext.springboot.springproducer.entity.Order;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: xting
 * @CreateDate: 2019/5/27 16:31
 */
@Component
public class OrderReceiver {


    /**
     * 建议将这里配置到配置文件
     * @param order
     * @param headers
     * @param channel
     * @throws Exception
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "order-queue",durable = "true"),
            exchange = @Exchange(value = "order-exchange",durable = "true",type = "topic"),
            key = "order.*"


             )
    )
    @RabbitHandler
    public void onOrderMessage(@Payload Order order,
                               @Headers Map<String,Object> headers,
                               Channel channel) throws Exception{
        try {

        //消费方操作
        System.out.println("----收到消息,开始消费-------");
        System.out.println("订单ID："+order.getId());
        Long deliverTag = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
        //ACK
        channel.basicAck(deliverTag,true);
        }finally {
            channel.close();
        }


    }

}
