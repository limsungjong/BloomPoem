package com.example.bloompoem.repository;

import com.example.bloompoem.entity.PickUpOrderDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PickUpOrderDetailRepository extends JpaRepository<PickUpOrderDetailEntity, Long> {
}
