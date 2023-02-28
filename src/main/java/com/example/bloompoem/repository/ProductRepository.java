package com.example.bloompoem.repository;


import com.example.bloompoem.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity,Integer> {

}
