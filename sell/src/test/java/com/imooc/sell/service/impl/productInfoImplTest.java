package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class productInfoImplTest {
    @Autowired
    private ProductInfoServiceImpl productInfoService;

    @Test
    public void findOne() {
        ProductInfo info = productInfoService.findOne("123456");
        Assert.assertEquals("123456",info.getProductId());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> lists = productInfoService.findUpAll();
        Assert.assertNotEquals(0,lists.size());
    }

    @Test
    public void findAll() {
        PageRequest request = new PageRequest(0,2);
        Page<ProductInfo> page = productInfoService.findAll(request);
//        System.out.println(page.getTotalElements());
        Assert.assertNotEquals(0,page.getTotalElements());

    }

    @Test
    public void save() {

        ProductInfo info = new ProductInfo();
        info.setProductId("123478");
        info.setProductName("皮皮虾");
        info.setProductPrice(new BigDecimal(6.5));
        info.setCategoryType(3);
        info.setProductDescription("很好吃的虾");
        info.setProductStatus(0);
        info.setProductStock(300);
        info.setProductIcon("http://yx/jpg");
        ProductInfo result = productInfoService.save(info);
        Assert.assertNotNull(result);
    }
}