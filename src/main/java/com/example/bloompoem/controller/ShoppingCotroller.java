package com.example.bloompoem.controller;

import com.example.bloompoem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShoppingCotroller {
    @Autowired
    private ProductService productService;


    @GetMapping("/shopping")
    public String shopping (){

        return "/shop/shopMain";
    }
    @GetMapping("/shopping/category")
    public String shoppingCategory (){

        return "/shop/shopCategory";
    }

}
