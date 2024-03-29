package com.example.bloompoem.service;

import com.example.bloompoem.entity.BouquetColor;
import com.example.bloompoem.entity.BouquetEntity;
import com.example.bloompoem.repository.BouquetColorRepository;
import com.example.bloompoem.repository.BouquetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BouquetService {

    private final BouquetRepository BouquetDao;

    private final BouquetColorRepository bouquetColorDao;

    public List<BouquetColor> colorAllView(){
        return bouquetColorDao.findAll();
    }

    public int saveBouqet(BouquetEntity bouquet){
        return  BouquetDao.save(bouquet).getBouquetNumber();
    }

    public BouquetEntity selelctBouquet(int bouquetNumber){
        return  BouquetDao.findByBouquetNumber(bouquetNumber);
    }
}