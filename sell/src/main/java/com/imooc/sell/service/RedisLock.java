package com.imooc.sell.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @Author: xting
 * @CreateDate: 2019/5/17 14:15
 */
@Component
@Slf4j
public class RedisLock {

    @Autowired
    private StringRedisTemplate template;

    /**
     * 加锁
     * @param key
     * @param value 当前时间+超时时间
     * @return
     */
    public boolean lock(String key,String value){

        //使用redis的setnx命令，这里如果设置成功，返回true说明已经被锁住
        /**
         * 如果被锁，那么新值设置不进去
         */
        if(template.opsForValue().setIfAbsent(key,value)){
            return true;
        }

        /**
         * 下面这段代码如果不加，可能发生死锁的情况
         */
        //currentValue=A   这两个线程的value都是B  其中一个线程拿到锁
        String currentValue = template.opsForValue().get(key);
        //如果锁过期
        if(!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()){
            //使用getset方法，获取上一个锁的时间
            String oldValue = template.opsForValue().getAndSet(key, value);
            if(!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)){
                return true;
            }
        }

        return false;
    }


    //解锁
    public void unlock(String key,String value){
        try {
            String currentValue = template.opsForValue().get(key);
            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)) {
                template.opsForValue().getOperations().delete(key);
            }
        }catch(Exception e){
//            log.error("【redis分布式锁】解锁异常, {}", e);
        }

    }

}
