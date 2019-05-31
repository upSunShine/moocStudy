package com.imooc.sell.enums;

public enum ResultEnum {
    SUCCESS(0,"成功"),
    PARAM_ERROR(1,"参数错误"),
    PRODUCT_NOT_EXSISTS(-1,"商品不存在"),
    PRODUCT_STOCK_ERROR(-2,"商品库存不正确"),
    ORDER_NOT_EXSISTS(-3,"订单不存在"),
    DETAIL_NOT_EXSISTS(-4,"详情不存在"),
    ORDER_STATUS_ERROR(-5,"订单状态不正确"),
    ORDER_UPDATE_FAIL(-6,"订单更新失败"),
    ORDER_DETAIL_EMPTY(-7,"订单无商品详情"),
    ORDER_PAY_STATUS_ERROR(-8,"订单支付状态不正确"),
    CART_EMPTY(-9,"购物车为空"),
    ORDER_OWNER_ERROR(-10,"该订单不属于当前用户"),
    PRODUCT_STATUS_ERROR(-11,"商品状态不正确"),
    ORDER_CANCEL_SUCCESS(2,"订单取消成功"),
    ORDER_FINISH_SUCCESS(3,"订单完结成功"),
    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }



    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
