package com.example.bloompoem.service;

import com.example.bloompoem.dto.QnADTO;
import com.example.bloompoem.entity.QnAEntity;
import com.example.bloompoem.repository.QnARepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QnAService {
    QnARepository qnaRepository;

    // QnA 리스트 가져오기
    public Page<QnAEntity> getQnAList(Pageable pageable){
        Page<QnAEntity> qnAEntityPage = qnaRepository.findAll(pageable);
        return qnAEntityPage;
    }
}
