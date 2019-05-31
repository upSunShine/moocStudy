package com.imooc.sell.service;

import com.imooc.sell.dto.OrderDTO;

/**
 * @Author: xting
 * @CreateDate: 2019/5/13 10:12
 */
public interface BuyerService {

    OrderDTO findOrderOne(String openid,String orderId);

    OrderDTO orderCancel(String openid,String orderId);
}
