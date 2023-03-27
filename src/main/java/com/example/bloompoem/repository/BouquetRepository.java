package com.example.bloompoem.repository;

import com.example.bloompoem.entity.BouquetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BouquetRepository  extends JpaRepository<BouquetEntity , Integer> {

    BouquetEntity findByBouquetNumber(int bouquetNumber);
}
