package com.example.bloompoem.repository;

import com.example.bloompoem.entity.FloristReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FloristReviewRepository extends JpaRepository<FloristReviewEntity, Integer> {
    boolean existsByPickUpOrderNumber(int pickUpOrderNumber);
    Page<FloristReviewEntity> findByUserEmailOrderByFloristReviewRegDateDesc(String userEmail , Pageable pageable);

    List<FloristReviewEntity> findAllByFloristNumber(int floristNumber);
}
