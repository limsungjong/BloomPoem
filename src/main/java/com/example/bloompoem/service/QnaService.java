package com.example.bloompoem.service;

import com.example.bloompoem.dto.QnaDTO;
import com.example.bloompoem.entity.QnaEntity;
import com.example.bloompoem.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QnaService {

    private final QnaRepository qnaRepository;

    // Qna 리스트 가져오기
    public Page<QnaEntity> getQnaList(Pageable pageable){
        Page<QnaEntity> qnaEntityPage = qnaRepository.findAll(pageable);

        return qnaEntityPage;
    }

    // Qna 글작성
    public void write(QnaEntity qnaEntity){
//        QnaEntity qnaEntity = QnaEntity.toEntity(qnaDTO);

        int qnaNumber = qnaRepository.save(qnaEntity).getQnaNumber();
        qnaEntity.setQnaGroup(qnaNumber);
        qnaRepository.save(qnaEntity);
    }

    // Qna 글수정

}
