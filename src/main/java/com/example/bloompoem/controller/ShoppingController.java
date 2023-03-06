package com.example.bloompoem.controller;

import com.example.bloompoem.entity.ProductEntity;
import com.example.bloompoem.service.ProductService;

import jdk.jfr.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;

@Controller
public class ShoppingController {
    @Autowired
    private ProductService productService;

    private static Logger logger = LoggerFactory.getLogger(ShoppingController.class);

    @GetMapping("/shopping")
    public String shopping (){

        return "/shop/shopMain";
    }

    @GetMapping("/shopping/category")
    public String shoppingCategory (Model model, int category,String searchValue){
        model.addAttribute("category", category);
        model.addAttribute("page" , 1);
        model.addAttribute("searchValue", searchValue);
        if(category > 9 && category<0){
            return  "/shopping";
        }
        return "/shop/shopCategory";
    }

    //카테고리별 물건 불러오기
    @GetMapping("/shopping/productListView")
    public ResponseEntity<Page<ProductEntity>> view (int category , @PageableDefault(size = 6) Pageable pageable) {

        if(category != '\u0000'){
            Page<ProductEntity> product = productService.categoryProductView(category, pageable);
            if(product != null){
                return ResponseEntity.ok(product);
            }else {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
            }

        }else{
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
        }
    }
    @GetMapping("/shopping/product_detail")
    public String productDetail (Model model,int category, int productNumber) {
        ProductEntity product =null;
        if (category != 0 && productNumber != 0) {
            product = productService.viewOne(productNumber);
            model.addAttribute("product", product);
            model.addAttribute("productNumber", productNumber);
            model.addAttribute("category", category);
            return "/shop/shopProductDetail";
        } else {
            return "/shop/shopMain";
        }
    }
    @GetMapping("/shopping/search")
    public ResponseEntity<Page<ProductEntity>> searchProduct (String searchValue, @PageableDefault(size = 6)Pageable pageable){

        Page<ProductEntity> product = productService.searchProduct(searchValue, pageable);
        return ResponseEntity.ok(product);
    }
}
