package com.imooc.sell.dataobject.mapper;

import com.imooc.sell.dataobject.ProductCategory;

/**
 * @Author: xting
 * @CreateDate: 2019/5/17 9:35
 */
public interface ProductCategoryMapper {

    ProductCategory selectByCategoryType(Integer categoryType);
}
