package com.imooc.sell.dto;

/**
 * 购物车
 * @Author: xting
 * @CreateDate: 2019/5/8 15:53
 */
public class CartDTO {

    private String productId;

    private Integer productQuantity;

    public CartDTO(){

    }

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }
}
