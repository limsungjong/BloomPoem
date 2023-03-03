package com.example.bloompoem.service;

import com.example.bloompoem.dto.QnADTO;
import com.example.bloompoem.entity.QnAEntity;
import com.example.bloompoem.repository.QnARepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QnAService {
    QnARepository qnaRepository;

    // QnA 리스트 가져오기
    public List<QnADTO> findAll(){
        List<QnAEntity> qnaEntityList = qnaRepository.findAll();
        List<QnADTO> qnaDTOList = new ArrayList<>();
        for (QnAEntity qnaEntity : qnaEntityList){
            QnADTO qnaDTO = QnADTO.toDTO(qnaEntity);

            qnaDTOList.add(qnaDTO);
        }
        return qnaDTOList;
    }
}
