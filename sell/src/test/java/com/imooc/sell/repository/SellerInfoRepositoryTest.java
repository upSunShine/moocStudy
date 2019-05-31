package com.imooc.sell.repository;

import com.imooc.sell.dataobject.SellerInfo;
import com.imooc.sell.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoRepositoryTest {

    @Autowired
    private SellerInfoRepository repository;

    @Test
    public void testSave(){
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setId(KeyUtil.getUniqueKey());
        sellerInfo.setOpenid("abs");
        sellerInfo.setUsername("admin");
        sellerInfo.setPassword("admin");

        SellerInfo result = repository.save(sellerInfo);
        Assert.assertNotNull(result);


    }

    @Test
    public void findByOpenId() {
        SellerInfo sellerInfo = repository.findByOpenid("abs");
        Assert.assertEquals(sellerInfo.getOpenid(),"abs");
    }
}