package com.imooc.sell.repository;

import com.imooc.sell.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 订单dao
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster,String>{

     Page<OrderMaster> findByBuyerOpenid(String openid, Pageable pageable);
}
