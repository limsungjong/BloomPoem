package com.example.bloompoem.repository;

import com.example.bloompoem.entity.PickUpOrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PickUpOrderDetailRepository extends JpaRepository<PickUpOrderDetail,Long> {
}
