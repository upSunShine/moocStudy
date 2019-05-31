package com.ext.springboot.springproducer.producer;

import com.ext.springboot.springproducer.constants.Constans;
import com.ext.springboot.springproducer.entity.Order;
import com.ext.springboot.springproducer.mapper.BrokerMessageLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: xting
 * @CreateDate: 2019/5/27 15:26
 *
 * 消息发送
 */
@Component
public class RabbitOrderSender {

    private static Logger logger = LoggerFactory.getLogger(RabbitOrderSender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private BrokerMessageLogMapper messageLogMapper;


    /**
     * Broker收到消息之后，调用该方法返回应答结果
     */
    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String s) {
            logger.info("correlationData"+correlationData);
            String messageId = correlationData.getId();
            logger.info("消息确认返回值："+ack);
            if(ack){
                messageLogMapper.changeBrokerMessageLogStatus(messageId, Constans.ORDER_SENG_SUCCESS,new Date());
            }else{
                //根据具体失败原因选择补偿或重试
                logger.info("异常处理：返回结果："+s);
            }


        }
    };

    /**
     * 发送消息 构建自定义对象消息
     * @param order
     * @throws Exception
     */
    public void sendOrder(Order order) throws Exception{

        //通过实现ConfirmCallback接口，将消息发送到Broker之后触发回调，确认消息是否正确到达Broker服务器，也就是确认是否正确到达exchange
        rabbitTemplate.setConfirmCallback(confirmCallback);

        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(order.getMessageId());

        rabbitTemplate.convertAndSend("order-exchange", //exchange
                "order.abcd", //routing key
                order,//消息体内容
                correlationData  //消息唯一id

        );
    }


}
