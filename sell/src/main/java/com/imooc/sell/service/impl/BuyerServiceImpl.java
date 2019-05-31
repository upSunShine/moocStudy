package com.imooc.sell.service.impl;

import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exceptions.SellException;
import com.imooc.sell.service.BuyerService;
import com.imooc.sell.service.OrderMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: xting
 * @CreateDate: 2019/5/13 10:15
 */
@Service
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderMasterService orderMasterService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {

        return checkOrderOwner(openid,orderId);
    }

    @Override
    public OrderDTO orderCancel(String openid, String orderId) {

        OrderDTO orderDTO =checkOrderOwner(openid,orderId);
        if(orderDTO == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXSISTS);
        }

        return orderMasterService.cancel(orderDTO);
    }


    private OrderDTO checkOrderOwner(String openid,String orderId){
        if(org.springframework.util.StringUtils.isEmpty(openid) || org.springframework.util.StringUtils.isEmpty(orderId)){
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        OrderDTO orderDTO = orderMasterService.findOne(orderId);
        if(orderDTO == null){
            return null;
        }
        //判断是否是当前用户的订单
        if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}
