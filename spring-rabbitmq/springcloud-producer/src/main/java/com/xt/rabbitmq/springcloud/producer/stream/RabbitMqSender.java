package com.xt.rabbitmq.springcloud.producer.stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Author: xting
 * @CreateDate: 2019/6/25 11:28
 */
@EnableBinding(Barista.class)
@Service
public class RabbitMqSender {

    @Autowired
    private Barista barista;


    public void sendMessage(Object message,Map<String,Object> properties){

        try{
            MessageHeaders headers = new MessageHeaders(properties);
            Message message1 = MessageBuilder.createMessage(message,headers);
            boolean sendStatus = barista.logout().send(message1);
            System.err.println("---------------------");
            System.err.println("发送数据：" + message + "sendStatus = "+ sendStatus);
        }catch (Exception e){
            System.err.println("----------error------------");
            e.printStackTrace();
            throw  new RuntimeException(e.getMessage());
        }
    }
}
