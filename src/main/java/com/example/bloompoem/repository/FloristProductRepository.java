package com.example.bloompoem.repository;

import com.example.bloompoem.entity.FloristEntity;
import com.example.bloompoem.entity.FlowerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FloristProductRepository extends JpaRepository<FloristEntity, FlowerEntity> {
}
