package com.example.bloompoem.service;


import com.example.bloompoem.entity.ProductEntity;
import com.example.bloompoem.entity.ShoppingCartEntity;
import com.example.bloompoem.repository.ProductRepository;
import com.example.bloompoem.repository.ShoppingCartRepository;
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
    private ProductRepository productDao;
    @Autowired
    private ShoppingCartRepository cartDao;
    private static Logger logger = LoggerFactory.getLogger(ProductService.class);

    public Page<ProductEntity> categoryProductView (int category ,Pageable pageable){
        Page<ProductEntity> product = null;

        try{
            product = productDao.findAllByProductCategory(category, pageable);
        }catch(Exception e){
            logger.error("[ProductService] categoryProductView Exception ", e);
        }
        return product;
    }
    public Page<ProductEntity> productView(Pageable pageable){
        Page<ProductEntity> product=null;
        try{
            product = productDao.findAll(pageable);
        }catch(Exception e){
            logger.error("[ProductService] ProductView Exception ", e);
        }
        return product;
    }
    public ProductEntity viewOne (int productNumber){
        ProductEntity product = productDao.findByProductNumber(productNumber);
        return product;
    }
    public Page<ProductEntity> searchProduct(String searchValue, Pageable pageable){
        Page<ProductEntity> product = productDao.findByProductNameContaining(searchValue, pageable);
        if(product == null){
            product= null;
        }
        return product;
    }
    public void saveCart(ShoppingCartEntity cart){
        cartDao.saveAndFlush(cart);
    }
    public boolean existsProduct(int productNumber, String userEmail){
        return cartDao.existsByProductNumberAndUserEmail(productNumber, userEmail);
    }
    public int countCart(String userEmail){
        return cartDao.countByUserEmail(userEmail);
    }
    public List<ShoppingCartEntity> viewCart(String userEmail){
        return cartDao.findByUserEmail(userEmail);
    }

}
