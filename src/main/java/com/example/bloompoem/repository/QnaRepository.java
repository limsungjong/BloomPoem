package com.example.bloompoem.repository;

import com.example.bloompoem.entity.QnaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface QnaRepository extends JpaRepository<QnaEntity, Integer> {

    QnaEntity findByQnaNumber(int qnaNumber);

    QnaEntity findByUserEmail(String userEmail);

    QnaEntity findByQnaTitleAndQnaContent(String qnaTitle, String qnaContent);

    Page<QnaEntity> findAllByUserEmailOrderByQnaOrderAsc(String userEmail, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE qna q\n" +
            "   SET q.qnaOrder = q.qnaOrder+ 1\n" +
            " WHERE q.qnaGroup = :qna_group\n" +
            "   AND q.qnaOrder > :qna_order")
    int qnaOrderUpdate(int qna_group, int qna_order);

}
