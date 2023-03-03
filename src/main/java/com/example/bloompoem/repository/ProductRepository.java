package com.example.bloompoem.repository;


import com.example.bloompoem.entity.ProductEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Integer> {

    Page<ProductEntity> findAllByProductCategory(int category, Pageable pageable);

    ProductEntity findByProductNumber(int productNumber);

    Page<ProductEntity> findByProductNameContaining(String searchValue, Pageable pageable);
}
