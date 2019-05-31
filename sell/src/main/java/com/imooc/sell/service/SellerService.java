package com.imooc.sell.service;

import com.imooc.sell.dataobject.SellerInfo;

/**
 * 卖家端service
 * @Author: xting
 * @CreateDate: 2019/5/16 14:57
 */
public interface SellerService {

    public SellerInfo findSellerInfoByopenid(String openid);
}
