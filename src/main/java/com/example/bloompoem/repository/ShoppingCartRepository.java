package com.example.bloompoem.repository;

import com.example.bloompoem.entity.ProductEntity;
import com.example.bloompoem.entity.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity , Integer> {

    int countByUserEmail(String userEmail);
    boolean existsByProductAndUserEmail(ProductEntity product ,String userEmail);
    List<ShoppingCartEntity> findByUserEmailOrderByShoppingCartNumberDesc(String userEmail);

    @Transactional
    int deleteByShoppingCartNumberAndUserEmail(Integer shoppingCartNumber  , String userEmail);

    ShoppingCartEntity findByShoppingCartNumber(Integer shoppingCartNumber);

    ShoppingCartEntity findByProductAndUserEmail(ProductEntity product, String userEmail);

}
