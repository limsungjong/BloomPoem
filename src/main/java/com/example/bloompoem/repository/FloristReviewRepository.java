package com.example.bloompoem.repository;

import com.example.bloompoem.entity.FloristReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FloristReviewRepository extends JpaRepository<FloristReviewEntity, Integer> {
    boolean existsByPickUpOrderNumber(int pickUpOrderNumber);
}
