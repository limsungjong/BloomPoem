package com.example.bloompoem.repository;

import com.example.bloompoem.entity.ShoppingOrder;
import com.example.bloompoem.entity.ShoppingOrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface ShoppingOrderDetailRepository extends JpaRepository<ShoppingOrderDetail, Integer> {

    List<ShoppingOrderDetail> findByUserEmailAndShoppingOrder(String userEmail, ShoppingOrder shoppingOrder);


}
