package com.example.bloompoem.repository;

import com.example.bloompoem.entity.QnaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QnaRepository extends JpaRepository<QnaEntity, Integer> {

    QnaEntity findByQnaNumber(int qnaNumber);

    QnaEntity findByUserEmail(String userEmail);
}
