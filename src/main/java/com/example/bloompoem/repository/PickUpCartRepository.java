package com.example.bloompoem.repository;

import com.example.bloompoem.entity.PickUpCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PickUpCartRepository extends JpaRepository<PickUpCartEntity, Long> {
}
