package com.example.bloompoem.controller;

import com.example.bloompoem.entity.ProductEntity;
import com.example.bloompoem.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ShoppingCotroller {
    @Autowired
    private ProductService productService;
    private static Logger logger = LoggerFactory.getLogger(ShoppingCotroller.class);

    @GetMapping("/shopping")
    public String shopping (){

        return "/shop/shopMain";
    }

    @GetMapping("/shopping/category")
    public String shoppingCategory (){

        return "/shop/shopCategory";
    }

    //카테고리별 물건 불러오기
    @GetMapping("/productListView")
    public List<ProductEntity> view (char category) {
        List<ProductEntity> product = productService.categoryProductView(category);

        return product;
    }
}
