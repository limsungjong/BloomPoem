package com.example.bloompoem.repository;

import com.example.bloompoem.entity.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity , Integer> {
    boolean existsByProductNumberAndUserEmail(int productNumber, String userEmail);

    int countByUserEmail(String userEmail);

    List<ShoppingCartEntity> findByUserEmail(String userEmail);
}
