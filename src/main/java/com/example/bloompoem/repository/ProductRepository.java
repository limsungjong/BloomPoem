package com.example.bloompoem.repository;


import com.example.bloompoem.entity.ProductEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Integer> {

    Page<ProductEntity> findAllByProductCategory(int category, Pageable pageable);

    ProductEntity findByProductNumber(int productNumber);

    Page<ProductEntity> findByProductNameContaining(String searchValue, Pageable pageable);

    ProductEntity findByProductName(String productName);

    @Query(value = "SELECT P.*\n" +
            "FROM PRODUCT P\n" +
            "WHERE P.PRODUCT_NUMBER IN (\n" +
            "  SELECT SOD.PRODUCT_NUMBER\n" +
            "  FROM SHOPPING_ORDER_DETAIL SOD\n" +
            "  GROUP BY SOD.PRODUCT_NUMBER\n" +
            "  ORDER BY COUNT(*) DESC\n" +
            "  FETCH FIRST 6 ROWS ONLY\n" +
            ")",nativeQuery = true)
    List<ProductEntity> shoppingMainManyProduct();
}
