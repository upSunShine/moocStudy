package com.imooc.sell.controller;


import com.imooc.sell.dto.OrderDTO;
import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exceptions.SellException;
import com.imooc.sell.service.OrderMasterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @Author: xting
 * @CreateDate: 2019/5/14 10:49
 */
@Controller
@RequestMapping("/seller/order")
@ResponseBody
public class SellController {

    @Autowired
    private OrderMasterService orderMasterService;

    /**
     *
     * @param page 第几页，从第一页开始
     * @param size 一页多少条数据
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "5") Integer size,
                             Map<String,Object> map) {

        Page<OrderDTO> orderDTOPage = orderMasterService.findList(PageRequest.of(page-1,size));
        map.put("orderDTOPage",orderDTOPage);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("order/list",map);


    }

    /**
     * 取消订单
     * @param orderId
     * @return
     */
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId,
                               Map<String,Object> map){
        try{
            OrderDTO orderDTO =  orderMasterService.findOne(orderId);
            orderMasterService.cancel(orderDTO);
        }
        catch(SellException e){
            //卖家端取消订单：查询不到订单
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("/commom/error",map);

        }
        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS.getMsg());
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("/commom/success",map);

    }


    /**
     * 订单详情
     * @param orderId
     * @param map
     * @return
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId,
                               Map<String,Object> map){

        OrderDTO orderDTO = new OrderDTO();
        try{
            orderDTO = orderMasterService.findOne(orderId);
        }
        catch(SellException e){
            //卖家端查订单详情：查询不到订单
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("/commom/error",map);

        }
        map.put("orderDTO", orderDTO);
        return new ModelAndView("/order/detail",map);

    }

    /**
     * 订单完结
     * @param orderId
     * @return
     */
    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam("orderId") String orderId,
                               Map<String,Object> map){
        try{
            OrderDTO orderDTO =  orderMasterService.findOne(orderId);
            orderMasterService.finish(orderDTO);
        }
        catch(SellException e){
            //卖家端完结订单：查询不到订单
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("/commom/error",map);

        }
        map.put("msg", ResultEnum.ORDER_FINISH_SUCCESS.getMsg());
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("/commom/success",map);

    }
}
