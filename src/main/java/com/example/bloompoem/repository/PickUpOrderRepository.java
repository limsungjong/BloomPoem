package com.example.bloompoem.repository;

import com.example.bloompoem.entity.PickUpOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PickUpOrderRepository extends JpaRepository<PickUpOrderEntity,Long> {
    Optional<List<PickUpOrderEntity>> findByUserEmail(String userEmail);
}
