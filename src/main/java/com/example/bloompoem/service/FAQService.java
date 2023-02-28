package com.example.bloompoem.service;

import com.example.bloompoem.dto.FAQDTO;
import com.example.bloompoem.entity.FAQEntity;
import com.example.bloompoem.repository.FAQRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FAQService {
    @Autowired
    private FAQRepository faqRepository;

    // FAQ 리스트 가져오기
    public List<FAQDTO> findAll(){
        List<FAQEntity> qnaEntityList = faqRepository.findAll();
        List<FAQDTO> faqDTOList = new ArrayList<>();
        for (FAQEntity faqEntity : qnaEntityList){
            FAQDTO faqDTO = FAQDTO.toDTO(faqEntity);
            faqDTOList.add(faqDTO);
        }
        return faqDTOList;
    }
}
