package com.imooc.sell.repository;

import com.imooc.sell.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;

    @Test
    public void testsave(){
        ProductInfo info = new ProductInfo();
        info.setProductId("123456");
        info.setProductName("皮蛋瘦肉粥");
        info.setProductPrice(new BigDecimal(4.5));
        info.setCategoryType(3);
        info.setProductDescription("男生最爱的粥");
        info.setProductStatus(0);
        info.setProductStock(100);
        info.setProductIcon("http://xxx/jpg");
        ProductInfo result = repository.save(info);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByProductStatus() {

        List<ProductInfo> infos = repository.findByProductStatus(0);
        Assert.assertNotEquals(0,infos.size());

    }
}