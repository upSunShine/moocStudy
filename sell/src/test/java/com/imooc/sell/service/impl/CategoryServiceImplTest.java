package com.imooc.sell.service.impl;

import com.imooc.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    public void findOne() {
        ProductCategory category = categoryService.findOne(1);
        Assert.assertEquals(new Integer(1),category.getCategoryId());

    }

    @Test
    public void findAll() {
        List<ProductCategory> lists = categoryService.findAll();
        Assert.assertNotEquals(0,lists.size());
    }

    @Test
    public void findByCategoryTypeIn() {
        List<ProductCategory> lists = categoryService.findByCategoryTypeIn(Arrays.asList(2,3,4));
        Assert.assertNotEquals(0,lists.size());
    }

    @Test
    public void save() {
        ProductCategory category = new ProductCategory("男生专享",4);
        ProductCategory result = categoryService.save(category);
        Assert.assertNotNull(result);
    }
}