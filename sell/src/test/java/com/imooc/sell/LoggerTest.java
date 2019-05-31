package com.imooc.sell;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
//@ContextConfiguration(value = {"classpath*:application.yml","classpath*:logback-spring.xml"})
public class LoggerTest {

    private final Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void test(){
        String name="zhangsan";
        String password = "123";
        logger.debug("ggggg");
        logger.error("ddddd");
        logger.info("ffff");
        logger.info("name: {},password: {}",name,password);
    }

}
