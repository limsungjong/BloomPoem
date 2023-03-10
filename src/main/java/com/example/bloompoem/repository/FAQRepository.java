package com.example.bloompoem.repository;

import com.example.bloompoem.entity.FAQEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FAQRepository extends JpaRepository<FAQEntity, Integer> {
    //@Query(value = "SELECT *   FROM FAQ   WHERE FAQ_NUMBER = faqNumber", nativeQuery = true);
}
