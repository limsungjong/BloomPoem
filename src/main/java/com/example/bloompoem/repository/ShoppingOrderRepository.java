package com.example.bloompoem.repository;

import com.example.bloompoem.entity.ShoppingOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingOrderRepository  extends JpaRepository<ShoppingOrder, Integer> {
}
