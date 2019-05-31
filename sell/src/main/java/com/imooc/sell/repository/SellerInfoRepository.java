package com.imooc.sell.repository;

import com.imooc.sell.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: xting
 * @CreateDate: 2019/5/16 14:42
 */
public interface SellerInfoRepository extends JpaRepository<SellerInfo,String> {

     SellerInfo findByOpenid(String openid);
}
