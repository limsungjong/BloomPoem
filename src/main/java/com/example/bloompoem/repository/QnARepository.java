package com.example.bloompoem.repository;

import com.example.bloompoem.entity.QnAEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QnARepository extends JpaRepository<QnAEntity, Integer> {
}
