package com.xt.rabbitmq.springcloud.consumer.Stream;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Author: xting
 * @CreateDate: 2019/6/25 14:06
 */
@EnableBinding(Barista.class)
@Service
public class RabbitMqReceiver {

    @StreamListener(Barista.INPUT_CHANNEL)
    public void receiver(Message message){

        Channel channel = (Channel) message.getHeaders().get(AmqpHeaders.CHANNEL);
        Long deliverTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        System.err.println("----------接收数据--------"+ message);
        System.err.println("消费完毕");
        try {
            channel.basicAck(deliverTag,false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
