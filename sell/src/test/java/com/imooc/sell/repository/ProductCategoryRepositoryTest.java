package com.imooc.sell.repository;

import com.imooc.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Test
    public void findone(){
        ProductCategory productCategory = productCategoryRepository.findById(1).get();
        System.out.print(productCategory);
    }

    @Test
//    @Transactional
    public void saveCategory(){
        ProductCategory productCategory = new ProductCategory("女生最爱",3);
        ProductCategory result = productCategoryRepository.save(productCategory);
        Assert.assertNotNull(result);
    }

    @Test
    public void updateCategory(){
        ProductCategory productCategory = productCategoryRepository.findById(2).get();
        productCategory.setCategoryName("男生最爱");
        productCategory.setCategoryType(3);
        productCategoryRepository.save(productCategory);
    }

    @Test
    public void testFindbyCategoryIn(){
        List<Integer> list = Arrays.asList(2,3,4);
        List<ProductCategory> lists = productCategoryRepository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,lists.size());
    }


}