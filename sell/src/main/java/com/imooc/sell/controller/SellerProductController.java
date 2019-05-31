package com.imooc.sell.controller;

import com.imooc.sell.dataobject.ProductCategory;
import com.imooc.sell.dataobject.ProductInfo;
import com.imooc.sell.exceptions.SellException;
import com.imooc.sell.form.ProductFrom;
import com.imooc.sell.service.CategoryService;
import com.imooc.sell.service.ProductInfoService;
import com.imooc.sell.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Author: xting
 * @CreateDate: 2019/5/15 15:04
 */
@Controller
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private CategoryService categoryService;


    /**
     *
     * @param page 第几页，从第一页开始
     * @param size 一页多少条数据
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value = "size",defaultValue = "5") Integer size,
                             Map<String,Object> map) {

        Page<ProductInfo> productInfoPage = productInfoService.findAll(PageRequest.of(page-1,size));
        map.put("productInfoPage",productInfoPage);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("product/list",map);

    }


    /**
     * 上架
     * @param productId
     * @return
     */
    @GetMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId,Map<String,Object> map){
        try{
            productInfoService.onSale(productId);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("/commom/error",map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("/commom/success",map);
    }

    /**
     * 下架
     * @param productId
     * @return
     */
    @GetMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId,Map<String,Object> map){
        try{
            productInfoService.offSale(productId);
        }catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("/commom/error",map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("/commom/success",map);
    }

    /**
     * 展示
     * @param productId
     * @param map
     * @return
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value="productId",required = false) String productId,Map<String,Object> map){

        if(!StringUtils.isEmpty(productId)){
            ProductInfo productInfo = productInfoService.findOne(productId);
            map.put("productInfo",productInfo);
        }

        //查询所有的类目
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList",categoryList);

        return new ModelAndView("/product/index",map);
    }

    /**
     * 保存和更新
     * @param productFrom
     * @param bindingResult
     * @param map
     * @return
     */
    @PostMapping("/save")
//    @CachePut(cacheNames = "productInfo",value = "1243") 返回的对象与存进去的对象应该一致
    @CacheEvict(cacheNames = "productInfo",value = "1243")
    public ModelAndView save(@Valid ProductFrom productFrom,
                             BindingResult bindingResult,
                             Map<String,Object> map){
        if(bindingResult.hasErrors()){
            map.put("",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("/commom/error",map);
        }
        ProductInfo productInfo = new ProductInfo();
        try{

            if(!StringUtils.isEmpty(productFrom.getProductId())){
                productInfo = productInfoService.findOne(productFrom.getProductId());
            }
            else{
                //productId为空，是新增，设置id
                productFrom.setProductId(KeyUtil.getUniqueKey());
            }
            BeanUtils.copyProperties(productFrom,productInfo);
            productInfoService.save(productInfo);
        }catch(SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("/commom/error",map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("/commom/success",map);
    }
}
