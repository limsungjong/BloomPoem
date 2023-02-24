package com.example.bloompoem.controller;

import com.example.bloompoem.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ProductCotroller {
    @Autowired
    private ProductService productService;


    public int hihello(){


        return 0;
    }
}
