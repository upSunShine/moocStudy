package com.imooc.sell.form;

import java.math.BigDecimal;

/**
 * @Author: xting
 * @CreateDate: 2019/5/16 9:55
 */
public class ProductFrom {

    /**产品id.*/
    private String productId;

    /**价格.*/
    private BigDecimal productPrice;

    /**名称.*/
    private String productName;

    /**库存.*/
    private Integer productStock;

    /**描述.*/
    private String productDescription;

    /**小图.*/
    private String productIcon;

    /**类目编号.*/
    private Integer categoryType;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductStock() {
        return productStock;
    }

    public void setProductStock(Integer productStock) {
        this.productStock = productStock;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductIcon() {
        return productIcon;
    }

    public void setProductIcon(String productIcon) {
        this.productIcon = productIcon;
    }

    public Integer getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(Integer categoryType) {
        this.categoryType = categoryType;
    }
}
