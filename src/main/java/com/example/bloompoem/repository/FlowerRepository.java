package com.example.bloompoem.repository;

import com.example.bloompoem.entity.FlowerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowerRepository extends JpaRepository<FlowerEntity, Integer> {

    Page<FlowerEntity> findAll(Pageable pageable);

}
