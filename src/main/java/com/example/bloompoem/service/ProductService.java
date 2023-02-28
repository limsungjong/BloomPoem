package com.example.bloompoem.service;

import com.example.bloompoem.entity.ProductEntity;
import com.example.bloompoem.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository dao;
    private static Logger logger = LoggerFactory.getLogger(ProductService.class);

    public List<ProductEntity> categoryProductView (char category){
        List<ProductEntity> product = null;

        try{
            product = dao.findByProductCategory(category);
        }catch(Exception e){
            logger.error("[ProductService] categoryProductView Exception ", e);
        }
        return product;
    }
}
