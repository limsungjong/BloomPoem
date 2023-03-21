package com.example.bloompoem.service;

import com.example.bloompoem.entity.FAQEntity;
import com.example.bloompoem.repository.FAQRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Component
@RequiredArgsConstructor
@Service
public class FAQService {
    private final FAQRepository faqRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // FAQ 리스트 가져오기
    public Page<FAQEntity> getFAQList(Pageable pageable){
        Page<FAQEntity> faqEntityPage = faqRepository.findAll(pageable);
        return faqEntityPage;
    }
}
