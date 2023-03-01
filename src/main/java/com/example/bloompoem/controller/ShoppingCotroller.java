package com.example.bloompoem.controller;

import com.example.bloompoem.entity.ProductEntity;
import com.example.bloompoem.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


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
    public String shoppingCategory (String category){


        return "/shop/shopCategory";
    }

    //카테고리별 물건 불러오기
    @GetMapping("/productListView")
    public ResponseEntity<List<ProductEntity>> view (char category , @PageableDefault(size = 15) Pageable pageable) {
        if("".equals(category)){
            List<ProductEntity> product = productService.categoryProductView(category);
        return ResponseEntity.ok(product);
        }else{
            List<ProductEntity> product = productService.productView();
            return ResponseEntity.ok(product);

        }

    }
}
