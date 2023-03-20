package com.example.bloompoem.repository;

import com.example.bloompoem.entity.QnaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QnaRepository extends JpaRepository<QnaEntity, Integer> {
//    @Query(value = "SELECT qna_number FROM Qna ", nativeQuery = true)
//    public Integer getQnaSequence();

    QnaEntity findByQnaNumber(int qnaNumber);


}
