package com.example.bloompoem.repository;

import com.example.bloompoem.entity.FAQEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FAQRepository extends JpaRepository<FAQEntity, int> {
    @Override
    List<FAQEntity> findAllByFAQEntity(FAQEntity faqEntity);
}
