package com.imooc.sell.repository;

import com.imooc.sell.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer>{

     List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
