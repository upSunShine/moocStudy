package com.ext.springboot.springproducer.producer;

import com.ext.springboot.springproducer.entity.Order;
import com.ext.springboot.springproducer.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderSenderTest {

    @Autowired
    private RabbitOrderSender orderSender;

    @Autowired
    private OrderService orderService;
    @Test
    public void sendOrder() throws  Exception{

        Order order = new Order();
        order.setId("20170527");
        order.setName("薯条订单");
        order.setMessageId(UUID.randomUUID().toString());
        orderSender.sendOrder(order);

    }

    /**
     * 测试订单创建
     */
    @Test
    public void createOrder(){
        Order order = new Order();
//        order.setId("20190544430");
        order.setName("佛尔订单");
        order.setMessageId(UUID.randomUUID().toString());
        try {
            orderService.createOrder(order);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}