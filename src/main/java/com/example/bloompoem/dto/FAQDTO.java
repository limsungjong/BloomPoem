package com.example.bloompoem.dto;

import com.example.bloompoem.entity.FAQEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FAQDTO {
    private int faqNumber;
    private String faqTitle;
    private String faqContent;

    public static FAQDTO toDTO(FAQEntity faqEntity){
        FAQDTO faqDTO = new FAQDTO();
        faqDTO.setFaqNumber(faqEntity.getFaqNumber());
        faqDTO.setFaqTitle(faqEntity.getFaqTitle());
        faqDTO.setFaqContent(faqEntity.getFaqContent());
        return faqDTO;
    }
}
