package com.ext.springboot.springproducer.service;

import com.ext.springboot.springproducer.constants.Constans;
import com.ext.springboot.springproducer.entity.BrokerMessageLog;
import com.ext.springboot.springproducer.entity.Order;
import com.ext.springboot.springproducer.mapper.BrokerMessageLogMapper;
import com.ext.springboot.springproducer.mapper.OrderMapper;
import com.ext.springboot.springproducer.producer.RabbitOrderSender;
import com.ext.springboot.springproducer.util.DateUtils;
import com.ext.springboot.springproducer.util.FastJsonConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author: xting
 * @CreateDate: 2019/5/29 14:11
 *
 * 订单业务实现
 */
@Service
public class OrderService {

    private static Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private BrokerMessageLogMapper messageLogMapper;

    @Autowired
    private RabbitOrderSender orderSender;

    /**
     * 创建订单
     * @param order
     */
    public void createOrder(Order order){

        try {
            //使用当前时间作为创建订单时间
            Date orderTime = new Date();
            //业务数据入库
            orderMapper.insert(order);
            //消息记录入库
            BrokerMessageLog brokerMessageLog = new BrokerMessageLog();
            //消息唯一id
            brokerMessageLog.setMessageId(order.getMessageId());
            //将消息体转换为json入库
            brokerMessageLog.setMessage(FastJsonConvertUtil.convertObject2Json(order));
            //设置消息状态为0，表示发送中
            brokerMessageLog.setStatus(Constans.ORDER_SENGDING);
            //超时时间为一分钟
            brokerMessageLog.setNextRetry(DateUtils.addMinutes(orderTime,Constans.ORDER_TIMEOUT));
            brokerMessageLog.setCreateTime(new Date());
            brokerMessageLog.setUpdateTime(new Date());
            messageLogMapper.insertSelective(brokerMessageLog);
            //发送消息
            orderSender.sendOrder(order);
        } catch (Exception e) {
            logger.error("订单业务异常：{}",e);
        }


    }
}
