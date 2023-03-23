package com.example.bloompoem.service;

import com.example.bloompoem.entity.QnaEntity;
import com.example.bloompoem.repository.QnaRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QnaService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final QnaRepository qnaRepository;

    // Qna 리스트 가져오기
    public Page<QnaEntity> getQnaList(Pageable pageable){
        Page<QnaEntity> qnaEntityPage = qnaRepository.findAll(pageable);
        return qnaEntityPage;
    }

    // 게시글 작성
    public void write(QnaEntity qnaEntity){
        int qnaNumber = qnaRepository.save(qnaEntity).getQnaNumber();
        qnaEntity.setQnaGroup(qnaNumber);
        qnaRepository.save(qnaEntity);
    }

    // 게시글 시퀀스 가져오기
    public QnaEntity findById(Integer qnaNumber){
        return qnaRepository.findByQnaNumber(qnaNumber);
    }

    // 게시글 삭제
    public void deleteById(Integer qnaNumber){
        qnaRepository.deleteById(qnaNumber);
    }

    // 게시글 수정
    public void update(QnaEntity qnaEntity){

    }

}
