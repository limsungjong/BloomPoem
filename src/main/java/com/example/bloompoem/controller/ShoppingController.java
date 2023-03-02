package com.example.bloompoem.controller;

import com.example.bloompoem.entity.ProductEntity;
import com.example.bloompoem.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String shoppingCategory (Model model, char category){
        model.addAttribute("category", category);
        return "/shop/shopCategory";
    }

    //카테고리별 물건 불러오기
    @GetMapping("/shopping/productListView")
    public ResponseEntity<List<ProductEntity>> view (int category ) {

        if(category != '\u0000'){
            List<ProductEntity> product = productService.categoryProductView(category);
            if(product != null){
                return ResponseEntity.ok(product);
            }else {
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
            }

        }else{
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
        }
    }
}
