package com.example.bloompoem.repository;

import com.example.bloompoem.entity.BouquetColor;
import com.example.bloompoem.entity.BouquetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BouquetColorRepository extends JpaRepository<BouquetColor, String> {
}