package com.ext.springboot.springproducer.task;

import com.ext.springboot.springproducer.constants.Constans;
import com.ext.springboot.springproducer.entity.BrokerMessageLog;
import com.ext.springboot.springproducer.entity.Order;
import com.ext.springboot.springproducer.mapper.BrokerMessageLogMapper;
import com.ext.springboot.springproducer.producer.RabbitOrderSender;
import com.ext.springboot.springproducer.util.FastJsonConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @Author: xting
 * @CreateDate: 2019/5/29 16:27
 * 消息补偿或重试
 *
 */
@Component
public class RetryMessageTasker {

    private static Logger logger = LoggerFactory.getLogger(RetryMessageTasker.class);

    @Autowired
    private RabbitOrderSender orderSender;

    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;


    /**
     * 定时任务
     */
    @Scheduled(initialDelay = 5000,fixedDelay = 10000)
    public void resend(){
        logger.info("---------定时任务开始---------------");
        //消息状态为0且已经过时的消息集合
        List<BrokerMessageLog> list = brokerMessageLogMapper.query4StatusAndTimeoutMessage();
        list.forEach(brokerMessageLog -> {
            //投递三次以上的消息
            if(brokerMessageLog.getTryCount() >= 3){
                //更新失败的消息
                brokerMessageLogMapper.changeBrokerMessageLogStatus(brokerMessageLog.getMessageId(), Constans.ORDER_SENG_FAILURE,new Date());
            }else{
                //重试投递消息，将重试次数递增
                brokerMessageLogMapper.update4ReSend(brokerMessageLog.getMessageId(),new Date());
                Order resendOrder = FastJsonConvertUtil.convertJson2Object(brokerMessageLog.getMessage(),Order.class);
                try {
                    orderSender.sendOrder(resendOrder);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("---------异常处理---------------");
                }
            }
        });

    }

}
