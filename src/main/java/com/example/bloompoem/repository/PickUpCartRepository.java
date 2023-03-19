package com.example.bloompoem.repository;

import com.example.bloompoem.entity.PickUpCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PickUpCartRepository extends JpaRepository<PickUpCartEntity, Integer> {
    List<PickUpCartEntity> findByUserEmail(String userEmail);
    Optional<PickUpCartEntity> findByUserEmailAndFlowerNumberAndFloristNumber(String userEmail, int flowerNumber, int floristNumber);

    Optional<PickUpCartEntity> findByUserEmailAndFlowerNumberAndFlowerCountAndFloristNumber
            (String userEmail, int flowerNumber, int flowerCount, int floristNumber);
    void deleteByUserEmailAndAndFlowerNumberAndFloristNumber(String userEmail, int flowerNumber, int floristNumber);
}
