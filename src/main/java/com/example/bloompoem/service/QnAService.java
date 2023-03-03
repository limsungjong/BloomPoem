package com.example.bloompoem.service;

import com.example.bloompoem.dto.QnADTO;
import com.example.bloompoem.entity.QnAEntity;
import com.example.bloompoem.repository.QnARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QnAService {
    @Autowired
    QnARepository qnaRepository;

    // QnA 리스트 가져오기
    public Page<QnAEntity> getQnAList(Pageable pageable){
        Page<QnAEntity> qnAEntityPage = qnaRepository.findAll(pageable);

        return qnAEntityPage;
        // Null Point Exception 방지를 위한 if문
//        Optional<QnAEntity> qnaEntityOptional = qnaRepository.findById(qnAEntityPage.getNumber());
//        if(qnaEntityOptional.isPresent()){
//            // 조회 결과가 있는 경우
//            // get(): optional 객체에 들어있는 객체를 가져옴.
//            QnAEntity qnaEntity = qnaEntityOptional.get();
//            return (Page<QnAEntity>) qnaEntity;
//        } else {
//            // 조회 결과가 없는 경우
//            return null;
//        }
    }
}
