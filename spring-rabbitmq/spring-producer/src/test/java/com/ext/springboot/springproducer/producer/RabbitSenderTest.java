package com.ext.springboot.springproducer.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitSenderTest {

    @Autowired
    private RabbitSender rabbitSender;

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");

    @Test
    public void tetstSendMsg() {

        Map<String,Object> map = new HashMap<>();
        map.put("number",123456);
        map.put("send_time", simpleDateFormat.format(new Date()));
        rabbitSender.send("hello rabbit for springboot",map);
    }
}