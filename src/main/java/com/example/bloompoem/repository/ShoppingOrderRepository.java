package com.example.bloompoem.repository;

import com.example.bloompoem.entity.ShoppingOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface ShoppingOrderRepository  extends JpaRepository<ShoppingOrder, Integer> {

    Page<ShoppingOrder> findAllByShoppingOrderDateBetweenAndUserEmailAndShoppingOrderStatusGreaterThanEqualOrderByShoppingOrderNumberDesc(LocalDate startDate, LocalDate endDate , Pageable pageable, String userEmail ,int status);

    List<ShoppingOrder> findAllByShoppingOrderDateBetweenAndUserEmailAndShoppingOrderStatusGreaterThanEqualOrderByShoppingOrderNumberDesc(LocalDate startDate, LocalDate endDate , String userEmail , int status);

    ShoppingOrder findByShoppingOrderNumber(int shoppingOrderNumber);
}
