package com.example.bloompoem.repository;


import com.example.bloompoem.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity,Integer> {

        public List<ProductEntity> findByProductCategory(char category);
}
