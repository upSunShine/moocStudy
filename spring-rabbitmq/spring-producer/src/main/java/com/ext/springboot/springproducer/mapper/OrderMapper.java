package com.ext.springboot.springproducer.mapper;

import com.ext.springboot.springproducer.entity.Order;
import org.springframework.stereotype.Repository;

/**
 * @Author: xting
 * @CreateDate: 2019/5/29 14:08
 *
 * 订单业务数据
 */
@Repository
public interface OrderMapper {

    void insert(Order order);
}
