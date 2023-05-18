package com.example.bloompoem.entity;

import com.example.bloompoem.dto.FAQDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "FAQ")

public class FAQEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int faqNumber;

    @Column(length = 100, nullable = false)
    private String faqTitle;

    @Column(length = 300, nullable = false)
    private String faqContent;

    public static FAQEntity getFAQListToEntity(FAQDTO faqDTO){
        FAQEntity faqEntity = new FAQEntity();
        faqEntity.setFaqNumber(faqDTO.getFaqNumber());
        faqEntity.setFaqTitle(faqDTO.getFaqTitle());
        faqEntity.setFaqContent(faqDTO.getFaqContent());
        return faqEntity;
    }
}
