package com.example.bloompoem.service;

import com.example.bloompoem.entity.ProductEntity;
import com.example.bloompoem.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository dao;
    private static Logger logger = LoggerFactory.getLogger(ProductService.class);

    public List<ProductEntity> categoryProductView (int category ,Pageable pageable){
        List<ProductEntity> product = null;

        try{
            product = dao.findAllByProductCategory(category, pageable);
        }catch(Exception e){
            logger.error("[ProductService] categoryProductView Exception ", e);
        }
        return product;
    }
    public Page<ProductEntity> productView(Pageable pageable){
        Page<ProductEntity> product=null;
        try{
            product = dao.findAll(pageable);
        }catch(Exception e){
            logger.error("[ProductService] ProductView Exception ", e);
        }
        return product;
    }
    public ProductEntity viewOne (int productNumber){
        ProductEntity product = dao.findByProductNumber(productNumber);
        return product;
    }
}
